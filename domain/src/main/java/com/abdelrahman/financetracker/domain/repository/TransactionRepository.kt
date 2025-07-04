package com.abdelrahman.financetracker.domain.repository

import com.abdelrahman.financetracker.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getAllTransactions(): Flow<List<Transaction>>
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)
    suspend fun getTransactionById(id: String): Transaction?
    suspend fun searchTransactions(searchQuery: String): List<Transaction>
    suspend fun getTransactionsByCategory(category: String): List<Transaction>
} 