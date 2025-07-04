package com.abdelrahman.financetracker.currencyconverter.domain.usecase

import com.abdelrahman.financetracker.currencyconverter.domain.plugin.CurrencyPluginRegistry
import javax.inject.Inject

class SetActiveProviderUseCase @Inject constructor(
    private val pluginRegistry: CurrencyPluginRegistry
) {
    suspend operator fun invoke(providerId: String) {
        pluginRegistry.setActiveProvider(providerId)
    }
} 