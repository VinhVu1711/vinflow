package com.vinh.vinflow.di

import android.content.Context
import androidx.room.Room
import com.vinh.vinflow.data.local.dao.TransactionDao
import com.vinh.vinflow.data.local.database.VinflowDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideVinflowDatabase(
        @ApplicationContext context: Context
    ): VinflowDatabase {
        return Room.databaseBuilder(
            context,
            VinflowDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    //TransactionImpl cần Dao
    //->provideTransactionDao cần VinflowDatabase
    //->provideVinflowDatabase tạo database
    //->database.transactionDao()
    //inject vào TransactionImpl
    @Provides
    fun provideTransactionDao(database: VinflowDatabase): TransactionDao {
        return database.transactionDao()
    }

    private const val DATABASE_NAME = "vinflow.db"
}
