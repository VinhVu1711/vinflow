package com.vinh.vinflow.data.local.projection

import com.vinh.vinflow.domain.model.TransactionType

//Projection được sử dụng khi query trả về 1 vài cột thay vì 1 entity, dữ liệu join nhiều bảng
//dữ liệu aggrerate như SUM, COUNT, GROUP BY, dữ liệu dashboard/statistics
//Sử dụng để tính tổng tiền theo từng Category với mỗi loại giao dịch

data class CategoryAmountProjection(
    val categoryId: Long,
    val categoryName: String,
    val type: TransactionType,
    val totalAmount: Long
)
