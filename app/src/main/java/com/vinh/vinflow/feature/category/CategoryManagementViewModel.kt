package com.vinh.vinflow.feature.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinh.vinflow.domain.model.Category
import com.vinh.vinflow.domain.model.TransactionType
import com.vinh.vinflow.domain.usecase.category.AddCategoryResult
import com.vinh.vinflow.domain.usecase.category.AddCategoryUseCase
import com.vinh.vinflow.domain.usecase.category.DeleteCategoryResult
import com.vinh.vinflow.domain.usecase.category.DeleteCategoryUseCase
import com.vinh.vinflow.domain.usecase.category.ObserveCategoriesUseCase
import com.vinh.vinflow.domain.usecase.category.UpdateCategoryResult
import com.vinh.vinflow.domain.usecase.category.UpdateCategoryUseCase
import com.vinh.vinflow.domain.usecase.category.CategoryValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CategoryManagementViewModel @Inject constructor(
    //VM cần nghiệp vụ nào thì inject usecase đó
    private val observeCategories: ObserveCategoriesUseCase,
    private val addCategory: AddCategoryUseCase,
    private val updateCategory: UpdateCategoryUseCase,
    private val deleteCategory: DeleteCategoryUseCase
) : ViewModel() {
    //Ở đây không sử dụng stateIn vì state phức tạp thì
    //MutableStateFlow(_uiState) + update dễ kiểm soát hơn
    //=======
    //private, mutable, VM được quyền sửa
    private val _uiState = MutableStateFlow(CategoryManagementUiState())

    //public, read only, UI chỉ được đọc
    //sử dụng asStateFlow để convert từ MutableStateFlow(có thể sửa) thành StateFlow(không thể sửa)
    val uiState: StateFlow<CategoryManagementUiState> = _uiState.asStateFlow()

    //Dành cho sự kiện 1 lần như snackbar
    //Sử dụng Channel để handle one time event. CHannel Buffered nghĩa là nếu VM gửi sớm
    //trước khi UI collect kịp thì event vẫn được giữ tạm trong buffer, không mất ngay
    private val events = Channel<CategoryManagementEvent>(Channel.BUFFERED)
    //Biến channel thành Flow để UI collect được
    val eventFlow = events.receiveAsFlow()

    //Biến này giúp giữ coroutine đang collect danh sách danh mục
    //Ví dụ như User chọn chi tiêu thì sẽ cancel job observe Thu nhập và lauch job mới Chi tiêu
    private var categoryJob: Job? = null

    //Chạy ngay khi VM được tạo
    init {
        //Khi mới vừa mở màn hình ra thì load danh sách category của type mặc định(Thu nhập)
        observeSelectedType()
    }

    //User bấm thu nhập/chi tiêu thì đổi selected type tương ứng
    //đóng dialog/delete nếu đang mở, cập nhật lại danh sách theo type mới
    fun onTypeSelected(type: TransactionType) {
        if (type == _uiState.value.selectedType) return
        _uiState.update {
            it.copy(
                selectedType = type,
                dialogState = CategoryDialogState.Hidden,
                deleteTarget = null
            )
        }
        observeSelectedType()
    }

    //User bấm thêm danh mục
    fun onAddClick() {
        _uiState.update {
            //Cập nhật dialogState, mở dialog với name rỗng
            it.copy(
                dialogState = CategoryDialogState.Add(name = ""),
                deleteTarget = null
            )
        }
    }

    //User bấm chỉnh sửa danh mục
    fun onEditClick(category: Category) {
        _uiState.update {
            //Mở dialog edit và fill sẵn tên category
            it.copy(
                dialogState = CategoryDialogState.Edit(
                    category = category,
                    name = category.name
                ),
                deleteTarget = null
            )
        }
    }

    //User gõ tên trong dialog
    fun onDialogNameChange(name: String) {
        //update name trong dialog state và xóa error cũ nếu có.
        _uiState.update {
            it.copy(dialogState = it.dialogState.withName(name))
        }
    }

    //User đóng dialog add/edit
    fun onDismissDialog() {
        //Nếu đang submit thì không cho đóng để tránh trạng thái nửa chừng.
        if (_uiState.value.isSubmitting) return
        _uiState.update { it.copy(dialogState = CategoryDialogState.Hidden) }
    }

    //User bấm lưu
    fun onSaveCategory() {
        val state = _uiState.value
        val dialog = state.dialogState
        if (!dialog.isVisible || !dialog.canSave || state.isSubmitting) return

        //Hàm này kiểm tra dialog đang là Add hay Edit, rồi gọi saveNewCategory hoặc saveExistingCategory.
        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true) }
            try {
                when (dialog) {
                    is CategoryDialogState.Add -> saveNewCategory(dialog.name, state.selectedType)
                    is CategoryDialogState.Edit -> saveExistingCategory(dialog.category, dialog.name)
                    CategoryDialogState.Hidden -> Unit
                }
            } catch (_: Exception) {
                showMessage("Không thể lưu danh mục. Vui lòng thử lại.")
            } finally {
                _uiState.update { it.copy(isSubmitting = false) }
            }
        }
    }

    //User bấm icon xóa
    fun onDeleteClick(category: Category) {
        //Hàm này set deleteTarget, tức là category đang chờ xác nhận xóa.
        _uiState.update {
            it.copy(
                deleteTarget = category,
                dialogState = CategoryDialogState.Hidden
            )
        }
    }

    //User hủy dialog xóa
    fun onDismissDelete() {
        //CLear deleteTarget
        if (_uiState.value.isSubmitting) return
        _uiState.update { it.copy(deleteTarget = null) }
    }

    //User xác nhận xóa.
    fun onConfirmDelete() {
        //Gọi DeleteCategoryUseCase, rồi xử lý result: thành công, category đang được dùng, id không hợp lệ, hoặc lỗi bất thường.
        val target = _uiState.value.deleteTarget ?: return
        if (_uiState.value.isSubmitting) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true) }
            try {
                when (deleteCategory(target.id)) {
                    DeleteCategoryResult.Success -> {
                        _uiState.update { it.copy(deleteTarget = null) }
                        showMessage("Đã xóa danh mục.")
                    }
                    DeleteCategoryResult.CategoryInUse -> {
                        showMessage("Không thể xóa danh mục đang được dùng.")
                    }
                    DeleteCategoryResult.InvalidId -> {
                        showMessage("Danh mục không hợp lệ.")
                    }
                }
            } catch (_: Exception) {
                showMessage("Không thể xóa danh mục. Vui lòng thử lại.")
            } finally {
                _uiState.update { it.copy(isSubmitting = false) }
            }
        }
    }

    //Collect list category theo selected type hiện tại
    private fun observeSelectedType() {
        categoryJob?.cancel()
        categoryJob = viewModelScope.launch {
            observeCategories(_uiState.value.selectedType).collect { categories ->
                _uiState.update {
                    it.copy(
                        categories = categories,
                        isLoading = false
                    )
                }
            }
        }
    }

    //Tạo object Category mới rồi gọi AddCategoryUseCase
    private suspend fun saveNewCategory(name: String, type: TransactionType) {
        val category = Category(
            name = name,
            type = type,
            iconName = null,
            colorHex = null,
            createdAt = 0L,
            updatedAt = 0L
        )
        when (val result = addCategory(category)) {
            is AddCategoryResult.Success -> {
                _uiState.update { it.copy(dialogState = CategoryDialogState.Hidden) }
                showMessage("Đã thêm danh mục.")
            }
            AddCategoryResult.DuplicateName -> {
                setDialogError("Tên danh mục đã tồn tại trong loại này.")
            }
            is AddCategoryResult.ValidationError -> {
                setDialogError(result.reason.toMessage())
            }
        }
    }

    //Copy category cũ với name mới rồi gọi UpdateCategoryUseCase
    private suspend fun saveExistingCategory(category: Category, name: String) {
        when (val result = updateCategory(category.copy(name = name))) {
            UpdateCategoryResult.Success -> {
                _uiState.update { it.copy(dialogState = CategoryDialogState.Hidden) }
                showMessage("Đã cập nhật danh mục.")
            }
            UpdateCategoryResult.DuplicateName -> {
                setDialogError("Tên danh mục đã tồn tại trong loại này.")
            }
            UpdateCategoryResult.InvalidId -> {
                setDialogError("Danh mục không hợp lệ.")
            }
            is UpdateCategoryResult.ValidationError -> {
                setDialogError(result.reason.toMessage())
            }
        }
    }

    //Gắn lỗi inline vào dialog, ví dụ trùng tên hoặc tên rỗng.
    private fun setDialogError(message: String) {
        _uiState.update { it.copy(dialogState = it.dialogState.withError(message)) }
    }

    //Gửi one-time event để UI hiện snackbar.
    private suspend fun showMessage(message: String) {
        events.send(CategoryManagementEvent.ShowMessage(message))
    }
}

//Toàn bộ trạng thái để vẽ màn quản lý danh mục
data class CategoryManagementUiState(
    //Đang xem thu nhập hay chi tiêu(mặc định thu nhập)
    val selectedType: TransactionType = TransactionType.INCOME,
    //danh sách category đang hiển thị.
    val categories: List<Category> = emptyList(),
    //dialog add/edit đang ở trạng thái nào.
    val dialogState: CategoryDialogState = CategoryDialogState.Hidden,
    //category nào đang chờ confirm delete.
    val deleteTarget: Category? = null,
    //có đang load list không.
    val isLoading: Boolean = true,
    //có đang thêm/sửa/xóa không.
    val isSubmitting: Boolean = false
)

//trạng thái riêng cho dialog add/edit category.
//Nó là sealed interface, nghĩa là dialog chỉ có một trong vài trạng thái hợp lệ
sealed interface CategoryDialogState {
    //Có đang hiện hay không
    val isVisible: Boolean
    //Thu thập text user đang nhập
    val name: String
    //Lỗi inline nếu có
    val errorMessage: String?
    //Name không rỗng thì được save
    val canSave: Boolean
        get() = name.isNotBlank()

    //Không hiện dialog
    data object Hidden : CategoryDialogState {
        override val isVisible: Boolean = false
        override val name: String = ""
        override val errorMessage: String? = null
    }
    //Hiện dialog thêm
    data class Add(
        override val name: String,
        override val errorMessage: String? = null
    ) : CategoryDialogState {
        override val isVisible: Boolean = true
    }
    //Hiện dialog Edit
    data class Edit(
        val category: Category,
        override val name: String,
        override val errorMessage: String? = null
    ) : CategoryDialogState {
        override val isVisible: Boolean = true
    }
}

//Event 1 lần
sealed interface CategoryManagementEvent {
    data class ShowMessage(val message: String) : CategoryManagementEvent
}


//Extension/helper function
private fun CategoryDialogState.withName(newName: String): CategoryDialogState {
    return when (this) {
        is CategoryDialogState.Add -> copy(name = newName, errorMessage = null)
        is CategoryDialogState.Edit -> copy(name = newName, errorMessage = null)
        CategoryDialogState.Hidden -> this
    }
}

private fun CategoryDialogState.withError(message: String): CategoryDialogState {
    return when (this) {
        is CategoryDialogState.Add -> copy(errorMessage = message)
        is CategoryDialogState.Edit -> copy(errorMessage = message)
        CategoryDialogState.Hidden -> this
    }
}

private fun CategoryValidationResult.toMessage(): String {
    return when (this) {
        CategoryValidationResult.InvalidName -> "Tên danh mục không được để trống."
        CategoryValidationResult.Valid -> ""
    }
}
