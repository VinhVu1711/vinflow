package com.vinh.vinflow.domain.usecase.category

import com.vinh.vinflow.domain.model.Category
import com.vinh.vinflow.domain.model.TransactionType
import com.vinh.vinflow.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    operator fun invoke(type: TransactionType? = null): Flow<List<Category>> {
        return repository.observeCategories(type)
    }
}
