package com.vinh.vinflow.domain.usecase.transaction

import com.vinh.vinflow.domain.model.Transaction
import com.vinh.vinflow.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(): Flow<List<Transaction>> = repository.observeTransactions()
}
