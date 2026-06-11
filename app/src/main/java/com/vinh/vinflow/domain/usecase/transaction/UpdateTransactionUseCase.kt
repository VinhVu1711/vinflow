package com.vinh.vinflow.domain.usecase.transaction

import com.vinh.vinflow.domain.model.Transaction
import com.vinh.vinflow.domain.repository.TransactionRepository
import javax.inject.Inject

class UpdateTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository,
    private val validateTransaction: ValidateTransactionUseCase
) {
    suspend operator fun invoke(transaction: Transaction): UpdateTransactionResult {
        if (transaction.id <= 0L) {
            return UpdateTransactionResult.InvalidId
        }

        return when (val validationResult = validateTransaction(transaction)) {
            TransactionValidationResult.Valid -> {
                repository.updateTransaction(
                    transaction.copy(updatedAt = System.currentTimeMillis())
                )
                UpdateTransactionResult.Success
            }
            else -> UpdateTransactionResult.ValidationError(validationResult)
        }
    }
}

sealed interface UpdateTransactionResult {
    data object Success : UpdateTransactionResult
    data object InvalidId : UpdateTransactionResult
    data class ValidationError(
        val reason: TransactionValidationResult
    ) : UpdateTransactionResult
}
