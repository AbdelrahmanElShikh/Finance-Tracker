package com.abdelrahman.financetracker.currencyconverter.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.abdelrahman.financetracker.currencyconverter.presentation.currencyConverter.CurrencyConverterScreen
import com.abdelrahman.financetracker.currencyconverter.presentation.settings.ProviderSettingsScreen

fun NavGraphBuilder.currencyConverterScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSettings: () -> Unit,
) {
    composable<CurrencyConverterRoute.CurrencyConverter> {
        CurrencyConverterScreen(
            onNavigateBack = onNavigateBack,
            onNavigateToSettings = onNavigateToSettings
        )
    }

    composable<CurrencyConverterRoute.CurrencySettings> {
        ProviderSettingsScreen(onNavigateBack)
    }
}