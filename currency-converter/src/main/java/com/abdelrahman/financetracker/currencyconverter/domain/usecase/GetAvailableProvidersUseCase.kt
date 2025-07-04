package com.abdelrahman.financetracker.currencyconverter.domain.usecase

import com.abdelrahman.financetracker.currencyconverter.domain.plugin.CurrencyPluginRegistry
import com.abdelrahman.financetracker.currencyconverter.domain.plugin.CurrencyProviderPlugin
import javax.inject.Inject

class GetAvailableProvidersUseCase @Inject constructor(
    private val pluginRegistry: CurrencyPluginRegistry
) {
    suspend operator fun invoke(): List<CurrencyProviderPlugin> {
        return pluginRegistry.getAllPlugins()
    }
} 