package com.vinh.vinflow.domain.usecase.category

import com.vinh.vinflow.domain.model.Category
import javax.inject.Inject

class ValidateCategoryUseCase @Inject constructor() {
    operator fun invoke(category: Category): CategoryValidationResult {
        return when {
            category.name.isBlank() -> CategoryValidationResult.InvalidName
            else -> CategoryValidationResult.Valid
        }
    }
}

sealed interface CategoryValidationResult {
    data object Valid : CategoryValidationResult
    data object InvalidName : CategoryValidationResult
}
