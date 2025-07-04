package com.abdelrahman.financetracker.transactions.list_transactions.intent

sealed class ListTransactionsIntent {
    data object LoadTransactions : ListTransactionsIntent()
    data object RefreshTransactions : ListTransactionsIntent()
}