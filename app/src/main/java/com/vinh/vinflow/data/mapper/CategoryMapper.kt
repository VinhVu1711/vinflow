package com.vinh.vinflow.data.mapper

import com.vinh.vinflow.data.local.entity.CategoryEntity
import com.vinh.vinflow.domain.model.Category

fun CategoryEntity.toDomain(): Category {
    return Category(
        id = id,
        name = name,
        type = type,
        iconName = iconName,
        colorHex = colorHex,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}

fun Category.toEntity() : CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        type = type,
        iconName = iconName,
        colorHex = colorHex,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}