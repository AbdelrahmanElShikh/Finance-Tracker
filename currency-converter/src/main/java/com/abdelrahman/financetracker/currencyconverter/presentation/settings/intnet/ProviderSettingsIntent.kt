package com.abdelrahman.financetracker.currencyconverter.presentation.settings.intnet

sealed class ProviderSettingsIntent {
    data object LoadProviders : ProviderSettingsIntent()
    data class SelectProvider(val providerId: String) : ProviderSettingsIntent()
    data object ClearError : ProviderSettingsIntent()
}