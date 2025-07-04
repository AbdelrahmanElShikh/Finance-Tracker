package com.abdelrahman.financetracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abdelrahman.financetracker.domain.model.Transaction
import com.abdelrahman.financetracker.domain.model.TransactionType

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey
    val id: String,
    val type: TransactionType,
    val category: String,
    val amount: Double,
    val date: String,
    val notes: String? = null
)

// Extension functions for mapping between domain and data layer
fun TransactionEntity.toDomainModel(): Transaction {
    return Transaction(
        id = id,
        type = type,
        category = category,
        amount = amount,
        date = date,
        notes = notes
    )
}

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        type = type,
        category = category,
        amount = amount,
        date = date,
        notes = notes
    )
} 