package com.vinh.vinflow.domain.usecase.transaction

import com.vinh.vinflow.domain.repository.TransactionRepository
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(id: Long): DeleteTransactionResult {
        if (id <= 0L) {
            return DeleteTransactionResult.InvalidId
        }

        repository.deleteTransaction(id)
        return DeleteTransactionResult.Success
    }
}

sealed interface DeleteTransactionResult {
    data object Success : DeleteTransactionResult
    data object InvalidId : DeleteTransactionResult
}
