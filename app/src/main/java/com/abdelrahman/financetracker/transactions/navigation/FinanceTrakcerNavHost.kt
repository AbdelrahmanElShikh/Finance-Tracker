package com.abdelrahman.financetracker.transactions.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.abdelrahman.financetracker.currencyconverter.navigation.CurrencyConverterRoute
import com.abdelrahman.financetracker.currencyconverter.navigation.currencyConverterScreen
import com.abdelrahman.financetracker.transactions.add_transaction.AddTransactionsScreen
import com.abdelrahman.financetracker.transactions.edit_transaction.EditTransactionsScreen
import com.abdelrahman.financetracker.transactions.list_transactions.ListTransactionsScreen

@Composable
fun FinanceTrackerNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TransactionRoute.ListTransactionRoute
    ) {
        composable<TransactionRoute.ListTransactionRoute> {
            ListTransactionsScreen(
                onNavigateToAdd = {
                    navController.navigate(TransactionRoute.AddTransactionRoute)
                },
                onNavigateToEdit = { transactionId ->
                    navController.navigate(TransactionRoute.EditTransactionRoute(transactionId))
                },
                onNavigateToCurrencyConverter = {
                    navController.navigate(CurrencyConverterRoute.CurrencyConverter)
                }
            )
        }

        composable<TransactionRoute.AddTransactionRoute> {
            AddTransactionsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<TransactionRoute.EditTransactionRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<TransactionRoute.EditTransactionRoute>()

            EditTransactionsScreen(
                transactionId = route.transactionId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        currencyConverterScreen(
            onNavigateBack = { navController.popBackStack() },
            onNavigateToSettings = { navController.navigate(CurrencyConverterRoute.CurrencySettings) })

    }
}


