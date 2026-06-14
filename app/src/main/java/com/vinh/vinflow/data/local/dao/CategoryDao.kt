package com.vinh.vinflow.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vinh.vinflow.data.local.entity.CategoryEntity
import com.vinh.vinflow.domain.model.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query(
        """
        SELECT * FROM categories
        WHERE (:type IS NULL OR type = :type)
        ORDER BY type ASC, name COLLATE NOCASE ASC
        """
    )
    fun observeCategories(type: TransactionType?): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE id = :id LIMIT 1")
    suspend fun getCategoryById(id: Long): CategoryEntity?

    @Query(
        """
        SELECT EXISTS(
            SELECT 1 FROM categories
            WHERE LOWER(name) = LOWER(:name)
                AND type = :type
                AND (:excludeId IS NULL OR id != :excludeId)
        )
        """
    )
    suspend fun isCategoryNameExists(
        name: String,
        type: TransactionType,
        excludeId: Long?
    ): Boolean

    @Query(
        """
        SELECT EXISTS(
            SELECT 1 FROM transactions
            WHERE categoryId = :id
        )
        """
    )
    suspend fun isCategoryInUse(id: Long): Boolean

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addCategory(category: CategoryEntity): Long

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    @Query("DELETE FROM categories WHERE id = :id")
    suspend fun deleteCategoryById(id: Long)

    @Query("SELECT COUNT(*) FROM categories")
    suspend fun getCategoryCount(): Int

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCategories(categories: List<CategoryEntity>)
}
