package com.abdelrahman.financetracker.domain.model

data class Transaction(
    val id: String,
    val type: TransactionType,
    val category: String,
    val amount: Double,
    val date: String,
    val notes: String? = null
)

enum class TransactionType {
    INCOME, EXPENSE
} 