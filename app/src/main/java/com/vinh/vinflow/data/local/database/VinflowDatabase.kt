package com.vinh.vinflow.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vinh.vinflow.data.local.converter.TransactionTypeConverter
import com.vinh.vinflow.data.local.dao.CategoryDao
import com.vinh.vinflow.data.local.dao.TransactionDao
import com.vinh.vinflow.data.local.entity.CategoryEntity
import com.vinh.vinflow.data.local.entity.TransactionEntity

@Database(
    entities = [
        CategoryEntity::class,
        TransactionEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(TransactionTypeConverter::class)
abstract class VinflowDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    abstract fun categoryDao(): CategoryDao
}
