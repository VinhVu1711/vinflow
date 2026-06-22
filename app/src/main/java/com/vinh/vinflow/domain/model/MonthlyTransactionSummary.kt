package com.vinh.vinflow.domain.model

//Domain version của MonthlyTransactionSummaryProjection
//Thống kê tài chính 1 tháng
data class MonthlyTransactionSummary(
    val monthKey: String,
    val totalIncome: Long,
    val totalExpense: Long,
    val balance: Long
)
