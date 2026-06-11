package com.vinh.vinflow.domain.usecase.transaction

import com.vinh.vinflow.domain.model.Transaction
import com.vinh.vinflow.domain.model.TransactionFilter
import com.vinh.vinflow.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFilteredTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(filter: TransactionFilter): Flow<List<Transaction>> {
        return repository.observeTransactionsByFilter(
            filter.copy(
                limit = filter.limit.coerceAtLeast(1),
                offset = filter.offset.coerceAtLeast(0),
                query = filter.query?.trim()?.takeIf { it.isNotEmpty() }
            )
        )
    }
}
