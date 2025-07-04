package com.abdelrahman.financetracker.currencyconverter.presentation.settings.state

import com.abdelrahman.financetracker.currencyconverter.domain.plugin.CurrencyProviderPlugin

data class ProviderSettingsState(
    val availableProviders: List<CurrencyProviderPlugin> = emptyList(),
    val activeProviderId: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)