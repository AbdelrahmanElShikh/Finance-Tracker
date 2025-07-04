package com.abdelrahman.financetracker.transactions.list_transactions.state

import com.abdelrahman.financetracker.domain.model.Transaction

data class ListTransactionsState(
    val transactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)