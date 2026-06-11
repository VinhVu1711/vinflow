package com.vinh.vinflow.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vinh.vinflow.data.local.entity.TransactionEntity
import com.vinh.vinflow.domain.model.TransactionType
import kotlinx.coroutines.flow.Flow


//Đây là nơi khai báo các thao tác Database: query
//Dao làm việc với entity chứ không làm viêc với domain
//Lí do sử dụng interface ở đây là không cần custom logic bên trong hàm
@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY date DESC, id DESC")
    fun observeTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions ORDER BY date DESC, id DESC LIMIT :limit")
    fun observeRecentTransactions(limit: Int): Flow<List<TransactionEntity>>

    @Query(
        """
        SELECT transactions.* FROM transactions
        LEFT JOIN categories ON transactions.categoryId = categories.id
        WHERE (:type IS NULL OR transactions.type = :type)
            AND (:categoryId IS NULL OR transactions.categoryId = :categoryId)
            AND transactions.date BETWEEN :startDate AND :endDate
            AND (
                :query IS NULL
                OR transactions.note LIKE '%' || :query || '%'
                OR categories.name LIKE '%' || :query || '%'
            )
        ORDER BY date DESC, transactions.id DESC
        LIMIT :limit OFFSET :offset
        """
    )
    fun observeTransactionsByFilter(
        type: TransactionType?,
        categoryId: Long?,
        startDate: Long,
        endDate: Long,
        query: String?,
        limit: Int,
        offset: Int
    ): Flow<List<TransactionEntity>>

    @Query(
        """
        SELECT COALESCE(SUM(amount), 0) FROM transactions
        WHERE type = :type AND date BETWEEN :startDate AND :endDate
        """
    )
    fun observeTotalByType(
        type: TransactionType,
        startDate: Long,
        endDate: Long
    ): Flow<Long>

    @Query("SELECT * FROM transactions WHERE id = :id LIMIT 1")
    suspend fun getTransactionById(id: Long): TransactionEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTransaction(transaction: TransactionEntity): Long

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)

    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransactionById(id: Long)
}
