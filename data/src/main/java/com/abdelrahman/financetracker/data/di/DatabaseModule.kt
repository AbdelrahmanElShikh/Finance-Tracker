package com.abdelrahman.financetracker.data.di

import android.content.Context
import androidx.room.Room
import com.abdelrahman.financetracker.data.local.dao.TransactionDao
import com.abdelrahman.financetracker.data.local.database.TransactionDatabase
import com.abdelrahman.financetracker.data.repository.TransactionRepositoryImpl
import com.abdelrahman.financetracker.domain.repository.TransactionRepository
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
    fun provideTransactionDatabase(
        @ApplicationContext context: Context
    ): TransactionDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            TransactionDatabase::class.java,
            "transaction_database"
        ).build()
    }
    
    @Provides
    fun provideTransactionDao(database: TransactionDatabase): TransactionDao {
        return database.transactionDao()
    }
    
    @Provides
    @Singleton
    fun provideTransactionRepository(
        transactionDao: TransactionDao
    ): TransactionRepository {
        return TransactionRepositoryImpl(transactionDao)
    }
} 