package com.abdelrahman.financetracker.transactions.add_transaction.state

import com.abdelrahman.financetracker.domain.model.TransactionType

data class AddTransactionState(
    val selectedType: TransactionType = TransactionType.EXPENSE,
    val selectedCategory: String = "",
    val amount: String = "",
    val date: String = "",
    val notes: String = "",
    val showCategoryDropdown: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val categories: List<String> = emptyList()
)