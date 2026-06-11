package com.vinh.vinflow.domain.model


//Gom các điều kiện lọc vào 1 object
data class TransactionFilter(
    val type: TransactionType? = null,
    val categoryId: Long? = null,
    val startDate: Long = 0L,
    val endDate: Long = Long.MAX_VALUE,
    val query: String? = null,
    val limit: Int = DEFAULT_LIMIT,
    val offset: Int = 0
) {
    companion object {
        const val DEFAULT_LIMIT = 100
    }
}
