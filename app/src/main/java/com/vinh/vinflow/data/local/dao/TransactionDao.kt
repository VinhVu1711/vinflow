package com.vinh.vinflow.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vinh.vinflow.data.local.entity.TransactionEntity
import com.vinh.vinflow.data.local.projection.CategoryAmountProjection
import com.vinh.vinflow.data.local.projection.MonthlyTransactionSummaryProjection
import com.vinh.vinflow.domain.model.TransactionType
import kotlinx.coroutines.flow.Flow


//Đây là nơi khai báo các thao tác Database: query
//Dao làm việc với entity chứ không làm viêc với domain
//Lí do sử dụng interface ở đây là không cần custom logic bên trong hàm
// Ý nghĩa từng hàm có thể check tại TransactionRepository
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

    @Query(
        """
        SELECT COALESCE(
            SUM(
                CASE
                    WHEN type = 'INCOME' THEN amount
                    ELSE -amount
                END
            ),
            0
        ) FROM transactions
        """
    )
    fun observeBalance(): Flow<Long>

    @Query(
        """
        SELECT
            categories.id AS categoryId,
            categories.name AS categoryName,
            categories.type AS type,
            COALESCE(SUM(transactions.amount), 0) AS totalAmount
        FROM transactions
        INNER JOIN categories ON transactions.categoryId = categories.id
        WHERE transactions.type = :type
            AND transactions.date BETWEEN :startDate AND :endDate
        GROUP BY categories.id, categories.name, categories.type
        ORDER BY totalAmount DESC
        """
    )
    fun observeAmountByCategory(
        type: TransactionType,
        startDate: Long,
        endDate: Long
    ): Flow<List<CategoryAmountProjection>>

    @Query(
        """
        SELECT
            strftime('%Y-%m', transactions.date / 1000, 'unixepoch') AS monthKey,
            COALESCE(SUM(CASE WHEN transactions.type = 'INCOME' THEN transactions.amount ELSE 0 END), 0) AS totalIncome,
            COALESCE(SUM(CASE WHEN transactions.type = 'EXPENSE' THEN transactions.amount ELSE 0 END), 0) AS totalExpense,
            COALESCE(SUM(CASE WHEN transactions.type = 'INCOME' THEN transactions.amount ELSE -transactions.amount END), 0) AS balance
        FROM transactions
        WHERE transactions.date BETWEEN :startDate AND :endDate
        GROUP BY monthKey
        ORDER BY monthKey DESC
        """
    )
    fun observeMonthlyTransactionSummary(
        startDate: Long,
        endDate: Long
    ): Flow<List<MonthlyTransactionSummaryProjection>>

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
