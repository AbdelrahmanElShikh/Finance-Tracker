package com.abdelrahman.financetracker.data.repository

import com.abdelrahman.financetracker.data.local.dao.TransactionDao
import com.abdelrahman.financetracker.data.local.entity.toDomainModel
import com.abdelrahman.financetracker.data.local.entity.toEntity
import com.abdelrahman.financetracker.domain.model.Transaction
import com.abdelrahman.financetracker.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionRepository {
    
    override fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
    
    override suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction.toEntity())
    }
    
    override suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction.toEntity())
    }
    
    override suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction.toEntity())
    }
    
    override suspend fun getTransactionById(id: String): Transaction? {
        return transactionDao.getTransactionById(id)?.toDomainModel()
    }
    
    override suspend fun searchTransactions(searchQuery: String): List<Transaction> {
        return transactionDao.searchTransactions(searchQuery).map { it.toDomainModel() }
    }
    
    override suspend fun getTransactionsByCategory(category: String): List<Transaction> {
        return transactionDao.getTransactionsByCategory(category).map { it.toDomainModel() }
    }
} 