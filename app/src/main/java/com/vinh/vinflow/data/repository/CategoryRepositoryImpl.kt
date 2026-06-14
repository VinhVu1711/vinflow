package com.vinh.vinflow.data.repository

import com.vinh.vinflow.data.local.dao.CategoryDao
import com.vinh.vinflow.data.mapper.toDomain
import com.vinh.vinflow.data.mapper.toEntity
import com.vinh.vinflow.domain.model.Category
import com.vinh.vinflow.domain.model.TransactionType
import com.vinh.vinflow.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {
    override fun observeCategories(type: TransactionType?): Flow<List<Category>> {
        return categoryDao.observeCategories(type).map { c -> c.map { it.toDomain() } }
    }

    override suspend fun getCategoryById(id: Long): Category? {
        return categoryDao.getCategoryById(id)?.toDomain()
    }

    override suspend fun isCategoryNameExists(
        name: String,
        type: TransactionType,
        excludeId: Long?
    ): Boolean {
        return categoryDao.isCategoryNameExists(name, type, excludeId)
    }

    //Kiểm tra category có đang được transaction nào sử dụng không?
    override suspend fun isCategoryInUse(id: Long): Boolean {
        return categoryDao.isCategoryInUse(id)
    }

    override suspend fun addCategory(category: Category): Long {
        return categoryDao.addCategory(category.toEntity())
    }

    override suspend fun updateCategory(category: Category) {
        return categoryDao.updateCategory(category.toEntity())
    }

    override suspend fun deleteCategory(id: Long) {
        categoryDao.deleteCategoryById(id)
    }

    override suspend fun getCategoryCount(): Int {
        return categoryDao.getCategoryCount()
    }

    override suspend fun insertCategories(categories: List<Category>) {
        categoryDao.insertCategories(categories.map { c -> c.toEntity() })
    }
}
