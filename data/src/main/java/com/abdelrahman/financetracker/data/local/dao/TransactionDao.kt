package com.abdelrahman.financetracker.data.local.dao

import androidx.room.*
import com.abdelrahman.financetracker.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>
    
    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: String): TransactionEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)
    
    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)
    
    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)
    
    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransactionById(id: String)
    
    @Query("""
        SELECT * FROM transactions 
        WHERE category LIKE '%' || :searchQuery || '%'
        OR notes LIKE '%' || :searchQuery || '%'
        OR CAST(amount AS TEXT) LIKE '%' || :searchQuery || '%'
        ORDER BY date DESC
    """)
    suspend fun searchTransactions(searchQuery: String): List<TransactionEntity>
    
    @Query("SELECT * FROM transactions WHERE category = :category ORDER BY date DESC")
    suspend fun getTransactionsByCategory(category: String): List<TransactionEntity>
} 