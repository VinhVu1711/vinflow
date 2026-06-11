package com.vinh.vinflow.data.repository

import com.vinh.vinflow.data.local.dao.TransactionDao
import com.vinh.vinflow.data.mapper.toDomain
import com.vinh.vinflow.data.mapper.toEntity
import com.vinh.vinflow.domain.model.Transaction
import com.vinh.vinflow.domain.model.TransactionFilter
import com.vinh.vinflow.domain.model.TransactionType
import com.vinh.vinflow.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionRepository {
    override fun observeTransactions(): Flow<List<Transaction>> {
        return transactionDao.observeTransactions()
            .map { transactions -> transactions.map { it.toDomain() } }
    }

    override fun observeRecentTransactions(limit: Int): Flow<List<Transaction>> {
        return transactionDao.observeRecentTransactions(limit)
            .map { transactions -> transactions.map { it.toDomain() } }
    }

    override fun observeTransactionsByFilter(filter: TransactionFilter): Flow<List<Transaction>> {
        return transactionDao.observeTransactionsByFilter(
            type = filter.type,
            categoryId = filter.categoryId,
            startDate = filter.startDate,
            endDate = filter.endDate,
            query = filter.query,
            limit = filter.limit,
            offset = filter.offset
        ).map { transactions -> transactions.map { it.toDomain() } }
    }

    override fun observeTotalByType(
        type: TransactionType,
        startDate: Long,
        endDate: Long
    ): Flow<Long> {
        return transactionDao.observeTotalByType(
            type = type,
            startDate = startDate,
            endDate = endDate
        )
    }

    override suspend fun getTransactionById(id: Long): Transaction? {
        return transactionDao.getTransactionById(id)?.toDomain()
    }

    override suspend fun addTransaction(transaction: Transaction): Long {
        return transactionDao.insertTransaction(transaction.toEntity())
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction.toEntity())
    }

    override suspend fun deleteTransaction(id: Long) {
        transactionDao.deleteTransactionById(id)
    }
}
