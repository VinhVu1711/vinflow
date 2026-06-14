package com.vinh.vinflow.di

import com.vinh.vinflow.data.repository.CategoryRepositoryImpl
import com.vinh.vinflow.data.repository.TransactionRepositoryImpl
import com.vinh.vinflow.domain.repository.CategoryRepository
import com.vinh.vinflow.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
//Có n hàm repo thì bind n hàm implementation
//để nói với Hilt là ai cần repo này thì lỗi impl này ra
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindTransactionRepository(
        implementation: TransactionRepositoryImpl
    ): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        implementation: CategoryRepositoryImpl
    ): CategoryRepository
}
