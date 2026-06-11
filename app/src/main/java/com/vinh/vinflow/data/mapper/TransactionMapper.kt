package com.vinh.vinflow.data.mapper

import com.vinh.vinflow.data.local.entity.TransactionEntity
import com.vinh.vinflow.domain.model.Transaction

//Bộ công cụ chuyển đổi giữa tầng domain và entity
//Luồng sẽ là Dao trả Entity, sau đó Repo sẽ map Entity thành Domain, sau đó Usecase/VM ở trên sử dụng
//Luồng thêm traansaction sẽ là Usecase sẽ đưa Transactio, sau đó repo map domain thành entity và dao sẽ insert


fun TransactionEntity.toDomain(): Transaction {
    return Transaction(
        id = id,
        amount = amount,
        type = type,
        categoryId = categoryId,
        note = note,
        date = date,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        amount = amount,
        type = type,
        categoryId = categoryId,
        note = note,
        date = date,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
