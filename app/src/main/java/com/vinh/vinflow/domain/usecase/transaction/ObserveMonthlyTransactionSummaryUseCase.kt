package com.vinh.vinflow.domain.usecase.transaction

import com.vinh.vinflow.domain.model.MonthlyTransactionSummary
import com.vinh.vinflow.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMonthlyTransactionSummaryUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(
        startDate: Long,
        endDate: Long
    ): Flow<List<MonthlyTransactionSummary>> {
        return repository.observeMonthlyTransactionSummary(
            startDate = startDate,
            endDate = endDate
        )
    }
}
