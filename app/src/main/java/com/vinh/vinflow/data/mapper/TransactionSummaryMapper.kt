package com.vinh.vinflow.data.mapper

import com.vinh.vinflow.data.local.projection.CategoryAmountProjection
import com.vinh.vinflow.data.local.projection.MonthlyTransactionSummaryProjection
import com.vinh.vinflow.domain.model.CategoryAmountSummary
import com.vinh.vinflow.domain.model.MonthlyTransactionSummary


//Chuyển data layer projection sang domain model
fun CategoryAmountProjection.toDomain(): CategoryAmountSummary {
    return CategoryAmountSummary(
        categoryId = categoryId,
        categoryName = categoryName,
        type = type,
        totalAmount = totalAmount
    )
}

fun MonthlyTransactionSummaryProjection.toDomain(): MonthlyTransactionSummary {
    return MonthlyTransactionSummary(
        monthKey = monthKey,
        totalIncome = totalIncome,
        totalExpense = totalExpense,
        balance = balance
    )
}
