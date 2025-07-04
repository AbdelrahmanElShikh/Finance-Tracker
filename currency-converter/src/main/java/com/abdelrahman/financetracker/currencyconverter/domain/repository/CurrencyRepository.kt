package com.abdelrahman.financetracker.currencyconverter.domain.repository

import com.abdelrahman.financetracker.currencyconverter.domain.model.Currency
import kotlinx.coroutines.flow.StateFlow
import java.math.BigDecimal

interface CurrencyRepository {
    
    /**
     * Get all supported currencies
     */
    suspend fun getSupportedCurrencies(): List<Currency>
    
    /**
     * Get exchange rate between two currencies
     */
    suspend fun getExchangeRate(fromCurrency: String, toCurrency: String): BigDecimal?
    
    /**
     * Convert currency amount
     */
    suspend fun convertCurrency(
        amount: BigDecimal,
        fromCurrency: String,
        toCurrency: String
    ): ConversionResult?
    
    /**
     * Get recent conversions
     */
    val recentConversions: StateFlow<List<ConversionResult>>
    
    /**
     * Clear recent conversions
     */
    suspend fun clearRecentConversions()
}

/**
 * Data class for conversion results
 */
data class ConversionResult(
    val fromCurrency: String,
    val toCurrency: String,
    val fromAmount: BigDecimal,
    val toAmount: BigDecimal,
    val rate: BigDecimal,
    val timestamp: Long
) 