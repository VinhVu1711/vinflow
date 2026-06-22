package com.vinh.vinflow.domain.usecase.transaction

import com.vinh.vinflow.domain.model.CategoryAmountSummary
import com.vinh.vinflow.domain.model.TransactionType
import com.vinh.vinflow.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAmountByCategoryUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(
        type: TransactionType,
        startDate: Long,
        endDate: Long
    ): Flow<List<CategoryAmountSummary>> {
        return repository.observeAmountByCategory(
            type = type,
            startDate = startDate,
            endDate = endDate
        )
    }
}
