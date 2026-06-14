package com.vinh.vinflow.domain.usecase.category

import com.vinh.vinflow.domain.model.Category
import com.vinh.vinflow.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoryByIdUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(id: Long): Category? {
        if (id <= 0L) {
            return null
        }

        return repository.getCategoryById(id)
    }
}
