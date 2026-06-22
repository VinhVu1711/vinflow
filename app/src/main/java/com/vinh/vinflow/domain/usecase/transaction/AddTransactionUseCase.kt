package com.vinh.vinflow.domain.usecase.transaction

import com.vinh.vinflow.domain.model.Transaction
import com.vinh.vinflow.domain.repository.TransactionRepository
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository,
    private val validateTransaction: ValidateTransactionUseCase
) {
    suspend operator fun invoke(transaction: Transaction): AddTransactionResult {
        return when (val validationResult = validateTransaction(transaction)) {
            TransactionValidationResult.Valid -> {
                val now = System.currentTimeMillis()
                val savedId = repository.addTransaction(
                    //Lý do chỉ copy để cập nhật id, create và update là do
                    //transaction truyền vào đã có sẵn các dữ liệu người dùng nhập
                    //use case chỉ cập nhật các thông tin tự quyết định khi thêm mới
                    transaction.copy(
                        id = 0,
                        createdAt = now,
                        updatedAt = now
                    )
                )
                AddTransactionResult.Success(savedId)
            }
            else -> AddTransactionResult.ValidationError(validationResult)
        }
    }
}

sealed interface AddTransactionResult {
    data class Success(val id: Long) : AddTransactionResult
    data class ValidationError(
        val reason: TransactionValidationResult
    ) : AddTransactionResult
}
