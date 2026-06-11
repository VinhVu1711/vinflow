package com.vinh.vinflow.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vinh.vinflow.domain.model.TransactionType

@Entity(
    tableName = "categories",
    indices = [
        Index(value = ["name", "type"], unique = true)
    ]
)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: TransactionType,
    val iconName: String?,
    val colorHex: String?,
    val createdAt: Long,
    val updatedAt: Long
)
