package com.abdelrahman.financetracker.currencyconverter.domain.plugin

import java.math.BigDecimal

/**
 * Simple plugin interface for currency converter providers
 */
interface CurrencyProviderPlugin {
    
    /**
     * Unique identifier for this provider
     */
    val id: String
    
    /**
     * Human-readable display name for this provider
     */
    val displayName: String
    
    /**
     * Get exchange rate between two currencies
     * @param fromCurrency Source currency code (e.g., "USD")
     * @param toCurrency Target currency code (e.g., "EUR")
     * @return Exchange rate as BigDecimal, or null if not available
     */
    suspend fun getRate(fromCurrency: String, toCurrency: String): BigDecimal?
} 