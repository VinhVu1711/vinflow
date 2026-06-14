package com.vinh.vinflow.domain.model

data class Category(
    val id: Long = 0,
    val name: String,
    val type: TransactionType,
    val iconName: String?, //Tên icon đại diện trong Category
    val colorHex: String?,
    val createdAt: Long,
    val updatedAt: Long
)
