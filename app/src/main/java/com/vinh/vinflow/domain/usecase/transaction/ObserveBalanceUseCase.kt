package com.vinh.vinflow.domain.usecase.transaction

import com.vinh.vinflow.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveBalanceUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(): Flow<Long> {
        return repository.observeBalance()
    }
}
