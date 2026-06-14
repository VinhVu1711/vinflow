package com.vinh.vinflow.domain.usecase.category

import com.vinh.vinflow.domain.model.Category
import com.vinh.vinflow.domain.repository.CategoryRepository
import javax.inject.Inject

class UpdateCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository,
    private val validateCategoryUseCase: ValidateCategoryUseCase
) {
    suspend operator fun invoke(category: Category): UpdateCategoryResult {
        if (category.id <= 0L) {
            return UpdateCategoryResult.InvalidId
        }

        return when (val validationResult = validateCategoryUseCase(category)) {
            CategoryValidationResult.Valid -> {
                val normalizedName = category.name.trim()
                val isDuplicate = repository.isCategoryNameExists(
                    name = normalizedName,
                    type = category.type,
                    excludeId = category.id
                )
                if (isDuplicate) {
                    return UpdateCategoryResult.DuplicateName
                }

                repository.updateCategory(
                    category.copy(
                        name = normalizedName,
                        updatedAt = System.currentTimeMillis()
                    )
                )
                UpdateCategoryResult.Success
            }
            else -> UpdateCategoryResult.ValidationError(validationResult)
        }
    }
}

sealed interface UpdateCategoryResult {
    data object Success : UpdateCategoryResult
    data object InvalidId : UpdateCategoryResult
    data object DuplicateName : UpdateCategoryResult
    data class ValidationError(
        val reason: CategoryValidationResult
    ) : UpdateCategoryResult
}
