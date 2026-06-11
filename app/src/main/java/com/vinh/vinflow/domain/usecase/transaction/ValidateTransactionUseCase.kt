package com.vinh.vinflow.domain.usecase.transaction

import com.vinh.vinflow.domain.model.Transaction
import javax.inject.Inject

class ValidateTransactionUseCase @Inject constructor() {
    operator fun invoke(transaction: Transaction): TransactionValidationResult {
        return when {
            transaction.amount <= 0L -> TransactionValidationResult.InvalidAmount
            transaction.categoryId <= 0L -> TransactionValidationResult.InvalidCategory
            transaction.date <= 0L -> TransactionValidationResult.InvalidDate
            else -> TransactionValidationResult.Valid
        }
    }
}

sealed interface TransactionValidationResult {
    data object Valid : TransactionValidationResult
    data object InvalidAmount : TransactionValidationResult
    data object InvalidCategory : TransactionValidationResult
    data object InvalidDate : TransactionValidationResult
}
