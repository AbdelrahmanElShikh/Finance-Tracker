package com.abdelrahman.financetracker.transactions.edit_transaction.state

import com.abdelrahman.financetracker.domain.model.Transaction
import com.abdelrahman.financetracker.domain.model.TransactionType

data class EditTransactionState(
    val originalTransaction: Transaction? = null,
    val selectedType: TransactionType = TransactionType.EXPENSE,
    val selectedCategory: String = "",
    val amount: String = "",
    val date: String = "",
    val notes: String = "",
    val showCategoryDropdown: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isUpdateSuccess: Boolean = false,
    val isDeleteSuccess: Boolean = false,
    val categories: List<String> = emptyList()
)
