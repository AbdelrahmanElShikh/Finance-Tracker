package com.abdelrahman.financetracker.transactions.edit_transaction.intent

import com.abdelrahman.financetracker.domain.model.TransactionType

sealed class EditTransactionIntent {
    data class LoadTransaction(val transactionId: String) : EditTransactionIntent()
    data class SelectType(val type: TransactionType) : EditTransactionIntent()
    data class SelectCategory(val category: String) : EditTransactionIntent()
    data class UpdateAmount(val amount: String) : EditTransactionIntent()
    data class UpdateDate(val date: String) : EditTransactionIntent()
    data class UpdateNotes(val notes: String) : EditTransactionIntent()
    data class ToggleDropdown(val show: Boolean) : EditTransactionIntent()
    data class ToggleDeleteDialog(val show: Boolean) : EditTransactionIntent()
    data object SaveTransaction : EditTransactionIntent()
    data object DeleteTransaction : EditTransactionIntent()
    data object ClearError : EditTransactionIntent()
}