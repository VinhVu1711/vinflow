package com.vinh.vinflow.data.local.converter

import androidx.room.TypeConverter
import com.vinh.vinflow.domain.model.TransactionType

//Do là SQlite chỉ lưu được các loại dữ liệu cơ bản
//Nên file này sẽ có ý nghĩa chuyển hóa từ enum sang dạng String để lưu
//và ngược lại khi đọc từ database thì sẽ convert lại enum
class TransactionTypeConverter {
    @TypeConverter
    fun fromTransactionType(type: TransactionType): String = type.name

    @TypeConverter
    fun toTransactionType(value: String): TransactionType = TransactionType.valueOf(value)
}
