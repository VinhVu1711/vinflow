package com.vinh.vinflow.domain.usecase.category

import com.vinh.vinflow.domain.model.Category
import com.vinh.vinflow.domain.repository.CategoryRepository
import javax.inject.Inject

class AddCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository,
    private val validateCategoryUseCase: ValidateCategoryUseCase
) {
    suspend operator fun invoke(category: Category): AddCategoryResult {
        return when (val validationResult = validateCategoryUseCase(category)) {
            CategoryValidationResult.Valid -> {
                val normalizedName = category.name.trim()
                val isDuplicate = repository.isCategoryNameExists(
                    name = normalizedName,
                    type = category.type
                )
                if (isDuplicate) {
                    return AddCategoryResult.DuplicateName
                }

                val now = System.currentTimeMillis()
                val saveId = repository.addCategory(
                    category.copy(
                        id = 0,
                        name = normalizedName,
                        createdAt = now,
                        updatedAt = now
                    )
                )
                AddCategoryResult.Success(saveId)
            }
            else -> AddCategoryResult.ValidationError(validationResult)
        }
    }
}

sealed interface AddCategoryResult {
    data class Success(val id: Long) : AddCategoryResult
    data object DuplicateName : AddCategoryResult
    data class ValidationError(
        val reason: CategoryValidationResult
    ) : AddCategoryResult
}
