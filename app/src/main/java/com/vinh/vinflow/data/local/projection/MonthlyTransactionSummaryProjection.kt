package com.vinh.vinflow.data.local.projection

//Đây là object ở data layer dùng để nhận kết quả query thống kê theo tháng từ Room
//Tính tổng thu, tổng chi và số dư của tháng
data class MonthlyTransactionSummaryProjection(
    //Tháng
    val monthKey: String,
    //Tổng thu trong tháng đó
    val totalIncome: Long,
    //Tổng chi trong tháng đó
    val totalExpense: Long,
    //Tổng thu - Tổng chi
    val balance: Long
)
