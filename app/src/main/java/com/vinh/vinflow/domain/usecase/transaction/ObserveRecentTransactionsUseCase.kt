package com.vinh.vinflow.domain.usecase.transaction

import com.vinh.vinflow.domain.model.Transaction
import com.vinh.vinflow.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveRecentTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(limit: Int = DEFAULT_LIMIT): Flow<List<Transaction>> {
        return repository.observeRecentTransactions(limit.coerceAtLeast(1))
    }

    private companion object {
        const val DEFAULT_LIMIT = 5
    }
}
