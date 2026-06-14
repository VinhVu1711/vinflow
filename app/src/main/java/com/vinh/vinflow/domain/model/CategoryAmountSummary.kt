package com.vinh.vinflow.domain.model

//Model biểu diễn tổng tiền theo từng category
data class CategoryAmountSummary(
    val categoryId: Long,
    val categoryName: String,
    val type: TransactionType,
    val totalAmount: Long
)
