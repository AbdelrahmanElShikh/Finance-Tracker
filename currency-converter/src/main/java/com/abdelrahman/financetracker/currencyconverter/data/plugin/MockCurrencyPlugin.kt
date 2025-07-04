package com.abdelrahman.financetracker.currencyconverter.data.plugin

import com.abdelrahman.financetracker.currencyconverter.domain.plugin.CurrencyProviderPlugin
import kotlinx.coroutines.delay
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Simple mock currency provider for offline usage and demos
 */
@Singleton
class MockCurrencyPlugin @Inject constructor() : CurrencyProviderPlugin {
    
    override val id: String = "mock"
    override val displayName: String = "Mock Provider"
    
    // Mock exchange rates (USD base)
    private val rates = mapOf(
        "USD" to BigDecimal("1.0"),
        "EUR" to BigDecimal("0.85"),
        "GBP" to BigDecimal("0.73"),
        "JPY" to BigDecimal("110.0"),
        "AUD" to BigDecimal("1.35"),
        "CAD" to BigDecimal("1.25"),
        "CHF" to BigDecimal("0.92"),
        "CNY" to BigDecimal("6.45"),
        "SEK" to BigDecimal("8.60"),
        "NOK" to BigDecimal("8.50"),
        "DKK" to BigDecimal("6.35"),
        "PLN" to BigDecimal("3.80"),
        "INR" to BigDecimal("74.0"),
        "KRW" to BigDecimal("1180.0"),
        "SGD" to BigDecimal("1.35"),
        "HKD" to BigDecimal("7.80"),
        "MXN" to BigDecimal("20.0"),
        "BRL" to BigDecimal("5.20")
    )
    
    override suspend fun getRate(fromCurrency: String, toCurrency: String): BigDecimal? {
        // Simulate network delay
        delay(100L + (0..200).random().toLong())
        
        val fromRate = rates[fromCurrency] ?: return null
        val toRate = rates[toCurrency] ?: return null
        
        // Calculate cross rate: (1 / fromRate) * toRate
        return toRate.divide(fromRate, 6, BigDecimal.ROUND_HALF_UP)
    }
} 