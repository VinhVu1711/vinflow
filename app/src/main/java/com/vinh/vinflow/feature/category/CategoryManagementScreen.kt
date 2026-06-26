package com.vinh.vinflow.feature.category

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.vinh.vinflow.core.designsystem.component.EmptyState
import com.vinh.vinflow.core.designsystem.component.VinflowButton
import com.vinh.vinflow.core.designsystem.component.VinflowContentFrame
import com.vinh.vinflow.core.designsystem.component.VinflowSectionHeader
import com.vinh.vinflow.domain.model.Category
import com.vinh.vinflow.domain.model.TransactionType

@Composable
fun CategoryManagementScreen(
    modifier: Modifier = Modifier,
    //Chỗ này không viết là CategoryManagementViewModel() bởi vì VM cần nhiều dependency
    //gồm các UseCase, nếu tự truyền thì sai với Hilt/MVVM pattern
    //Sử dụng = hiltViewModel() để Hilt giúp lấy đúng VM cho màn hình này, inject đúng UseCase
    //Ngoài ra còn giúp giữ VM sống qua recomposition, tự clear VM khi screen bị clear khỏi backstack
    viewModel: CategoryManagementViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    //SnackBar là 1 side effect, không phải 1 UI tĩnh
    //mà là hành động hiện thông báo 1 lần
    //Cả cụm eventFlow.collect là 1 suspend operation, compose không cho gọi trực tiếp cho UI
    //Nên cần LaunchedEffect
    LaunchedEffect(viewModel) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is CategoryManagementEvent.ShowMessage -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    //Bọc toàn màn hình trong 1 cái Box vì SnackbarHost đè ở dưới cùng màn hình, còn content chính nằm full screen
    Box(modifier = modifier.fillMaxSize()) {
        //Nội dung màn hình quản lý danh mục
        CategoryManagementContent(
            uiState = uiState,
            onTypeSelected = viewModel::onTypeSelected,
            onAddClick = viewModel::onAddClick,
            onEditClick = viewModel::onEditClick,
            onDeleteClick = viewModel::onDeleteClick,
            modifier = Modifier.fillMaxSize()
        )
        //Snackbar thông báo
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
    //Dialog hiện lên khi edit category
    CategoryEditorDialog(
        dialogState = uiState.dialogState,
        selectedType = uiState.selectedType,
        isSubmitting = uiState.isSubmitting,
        onNameChange = viewModel::onDialogNameChange,
        onDismiss = viewModel::onDismissDialog,
        //Cách viết này gọi là function reference trong Kotlin
        //Cách viết này tương đương onSave = { viewModel.onSaveCategory() }
        onSave = viewModel::onSaveCategory
    )
    //Dialog hiện lên khi delete category
    DeleteCategoryDialog(
        category = uiState.deleteTarget,
        isSubmitting = uiState.isSubmitting,
        onDismiss = viewModel::onDismissDelete,
        onConfirm = viewModel::onConfirmDelete
    )
}

// Toàn bộ màn hình quản lý danh mục
@Composable
fun CategoryManagementContent(
    uiState: CategoryManagementUiState,
    onTypeSelected: (TransactionType) -> Unit,
    onAddClick: () -> Unit,
    onEditClick: (Category) -> Unit,
    onDeleteClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    //Sử dụng component VinflowScaffold
    VinflowContentFrame(modifier = modifier) {
        //Sử dụng LazyColumn
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            //Có 1 hàng hiển thị loại danh mục
            item(key = "segments") {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CategorySegment(
                        text = "Thu nhập",
                        selected = uiState.selectedType == TransactionType.INCOME,
                        onClick = { onTypeSelected(TransactionType.INCOME) },
                        modifier = Modifier.weight(1f)
                    )
                    CategorySegment(
                        text = "Chi tiêu",
                        selected = uiState.selectedType == TransactionType.EXPENSE,
                        onClick = { onTypeSelected(TransactionType.EXPENSE) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            //Section Danh sách danh mục
            item(key = "section") {
                VinflowSectionHeader(text = "Danh sách danh mục")
            }
            //Handle case không có danh mục
            if (uiState.categories.isEmpty() && !uiState.isLoading) {
                item(key = "empty") {
                    EmptyState(
                        title = "Chưa có danh mục",
                        message = "Thêm danh mục đầu tiên cho loại đang chọn.",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            //Dữ liệu các danh mục
            items(
                items = uiState.categories,
                key = { category -> category.id }
            ) { category ->
                CategoryListItem(
                    category = category,
                    onEditClick = onEditClick,
                    onDeleteClick = onDeleteClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            //Button thêm
            item(key = "add") {
                VinflowButton(
                    text = "Thêm danh mục",
                    onClick = onAddClick,
                    leadingIcon = Icons.Default.Add,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

//Component sử dụng để hiển thị nút loại danh mục (Thu nhập/Chi tiêu)
@Composable
private fun CategorySegment(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .background(
                color = if (selected) {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)),
                RoundedCornerShape(8.dp)
            )
    ) {
        Text(
            text = text,
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

//Component card hiển thị danh mục và các thao tác với danh mục
@Composable
private fun CategoryListItem(
    category: Category,
    onEditClick: (Category) -> Unit,
    onDeleteClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)),
                RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = category.name,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = if (category.type == TransactionType.INCOME) "Thu nhập" else "Chi tiêu",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall
            )
        }
        IconButton(onClick = { onEditClick(category) }) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Sửa danh mục",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        IconButton(onClick = { onDeleteClick(category) }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Xóa danh mục",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}

// Dialog để chỉnh sửa danh mục
@Composable
fun CategoryEditorDialog(
    dialogState: CategoryDialogState,
    selectedType: TransactionType,
    isSubmitting: Boolean,
    onNameChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    if (!dialogState.isVisible) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = when (dialogState) {
                    is CategoryDialogState.Edit -> "Sửa danh mục"
                    else -> "Thêm danh mục"
                }
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = if (selectedType == TransactionType.INCOME) "Loại: Thu nhập" else "Loại: Chi tiêu",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
                OutlinedTextField(
                    value = dialogState.name,
                    onValueChange = onNameChange,
                    label = { Text("Tên danh mục") },
                    isError = dialogState.errorMessage != null,
                    supportingText = {
                        dialogState.errorMessage?.let { Text(text = it) }
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = onSave,
                enabled = dialogState.canSave && !isSubmitting
            ) {
                Text("Lưu")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isSubmitting
            ) {
                Text("Hủy")
            }
        }
    )
}
// Dialog để xóa danh mục
@Composable
private fun DeleteCategoryDialog(
    category: Category?,
    isSubmitting: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (category == null) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Xóa danh mục") },
        text = {
            Text("Bạn có chắc muốn xóa danh mục \"${category.name}\" không?")
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                enabled = !isSubmitting
            ) {
                Text("Xóa")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isSubmitting
            ) {
                Text("Hủy")
            }
        }
    )
}
