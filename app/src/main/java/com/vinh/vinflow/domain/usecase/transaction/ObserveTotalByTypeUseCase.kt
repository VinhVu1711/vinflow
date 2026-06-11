package com.vinh.vinflow.domain.usecase.transaction

import com.vinh.vinflow.domain.model.TransactionType
import com.vinh.vinflow.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTotalByTypeUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(
        type: TransactionType,
        startDate: Long,
        endDate: Long
    ): Flow<Long> {
        return repository.observeTotalByType(
            type = type,
            startDate = startDate,
            endDate = endDate
        )
    }
}
