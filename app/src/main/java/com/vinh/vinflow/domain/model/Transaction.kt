package com.vinh.vinflow.domain.model

data class Transaction(
    val id: Long = 0,
    val amount: Long,
    val type: TransactionType,
    val categoryId: Long,
    val note: String?,
    val date: Long,
    val createdAt: Long,
    val updatedAt: Long
)
