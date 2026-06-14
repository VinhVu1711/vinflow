package com.vinh.vinflow.domain.usecase.category

import com.vinh.vinflow.domain.repository.CategoryRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(id: Long): DeleteCategoryResult {
        if (id <= 0L) {
            return DeleteCategoryResult.InvalidId
        }

        if (repository.isCategoryInUse(id)) {
            return DeleteCategoryResult.CategoryInUse
        }

        repository.deleteCategory(id)
        return DeleteCategoryResult.Success
    }
}

sealed interface DeleteCategoryResult {
    data object Success : DeleteCategoryResult
    data object InvalidId : DeleteCategoryResult
    data object CategoryInUse : DeleteCategoryResult
}
