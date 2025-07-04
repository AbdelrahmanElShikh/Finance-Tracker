package com.abdelrahman.financetracker.transactions.navigation

import kotlinx.serialization.Serializable

sealed interface TransactionRoute {
    @Serializable
    data object ListTransactionRoute : TransactionRoute

    @Serializable
    data object AddTransactionRoute : TransactionRoute

    @Serializable
    data class EditTransactionRoute(val transactionId: String) : TransactionRoute

}
