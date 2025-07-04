package com.abdelrahman.financetracker.transactions.add_transaction.intent

import com.abdelrahman.financetracker.domain.model.TransactionType

sealed class AddTransactionIntent {
    data class SelectType(val type: TransactionType) : AddTransactionIntent()
    data class SelectCategory(val category: String) : AddTransactionIntent()
    data class UpdateAmount(val amount: String) : AddTransactionIntent()
    data class UpdateDate(val date: String) : AddTransactionIntent()
    data class UpdateNotes(val notes: String) : AddTransactionIntent()
    data class ToggleDropdown(val show: Boolean) : AddTransactionIntent()
    data object SaveTransaction : AddTransactionIntent()
    data object ResetForm : AddTransactionIntent()
    data object ClearError : AddTransactionIntent()
}