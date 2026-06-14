package com.vinh.vinflow.domain.repository

import com.vinh.vinflow.domain.model.Category
import com.vinh.vinflow.domain.model.TransactionType
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun observeCategories(type: TransactionType? = null): Flow<List<Category>>

    suspend fun getCategoryById(id: Long): Category?

    suspend fun isCategoryNameExists(
        name: String,
        type: TransactionType,
        excludeId: Long? = null
    ): Boolean

    suspend fun isCategoryInUse(id: Long): Boolean

    suspend fun addCategory(category: Category): Long

    suspend fun updateCategory(category: Category)

    suspend fun deleteCategory(id: Long)

    suspend fun getCategoryCount(): Int

    suspend fun insertCategories(categories: List<Category>)
}
