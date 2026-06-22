package com.vinh.vinflow.domain.repository

import com.vinh.vinflow.domain.model.Transaction
import com.vinh.vinflow.domain.model.CategoryAmountSummary
import com.vinh.vinflow.domain.model.MonthlyTransactionSummary
import com.vinh.vinflow.domain.model.TransactionFilter
import com.vinh.vinflow.domain.model.TransactionType
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    //Theo dõi toàn bộ transaction
    fun observeTransactions(): Flow<List<Transaction>>

    //Lấy các giao dịch gần nhất
    fun observeRecentTransactions(limit: Int): Flow<List<Transaction>>

    //Lấy lịch sử giao dịch theo điều kiện
    fun observeTransactionsByFilter(filter: TransactionFilter): Flow<List<Transaction>>

    //Tính tổng income hoặc expense trong khoảng ngày
    fun observeTotalByType(
        type: TransactionType,
        startDate: Long,
        endDate: Long
    ): Flow<Long>

    //Tổng số dư hiện tại Tổng Income - Tổng Expense
    fun observeBalance(): Flow<Long>

    //Tính tổng tiền theo từng Category
    fun observeAmountByCategory(
        type: TransactionType,
        startDate: Long,
        endDate: Long
    ): Flow<List<CategoryAmountSummary>>

    //Gom giao dịch từng tháng và tính tổng thu, tổng chi và số dư
    fun observeMonthlyTransactionSummary(
        startDate: Long,
        endDate: Long
    ): Flow<List<MonthlyTransactionSummary>>

    //Lấy 1 Transaction cụ thể
    suspend fun getTransactionById(id: Long): Transaction?

    //Thêm 1 Transaction
    suspend fun addTransaction(transaction: Transaction): Long

    //Cập nhật Transaction
    suspend fun updateTransaction(transaction: Transaction)

    //Xóa Transaction
    suspend fun deleteTransaction(id: Long)
}
