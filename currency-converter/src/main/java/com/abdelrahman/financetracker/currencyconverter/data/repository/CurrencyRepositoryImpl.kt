package com.abdelrahman.financetracker.currencyconverter.data.repository

import com.abdelrahman.financetracker.currencyconverter.domain.model.Currency
import com.abdelrahman.financetracker.currencyconverter.domain.plugin.CurrencyPluginRegistry
import com.abdelrahman.financetracker.currencyconverter.domain.repository.CurrencyRepository
import com.abdelrahman.financetracker.currencyconverter.domain.repository.ConversionResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Simple repository implementation for currency operations
 */
@Singleton
class CurrencyRepositoryImpl @Inject constructor(
    private val pluginRegistry: CurrencyPluginRegistry
) : CurrencyRepository {
    
    private val _recentConversions = MutableStateFlow<List<ConversionResult>>(emptyList())
    override val recentConversions: StateFlow<List<ConversionResult>> = _recentConversions.asStateFlow()
    
    override suspend fun getSupportedCurrencies(): List<Currency> {
        return Currency.getAllCurrencies()
    }
    
    override suspend fun getExchangeRate(fromCurrency: String, toCurrency: String): BigDecimal? {
        val activePlugin = pluginRegistry.getActivePlugin()
        return activePlugin?.getRate(fromCurrency, toCurrency)
    }
    
    override suspend fun convertCurrency(
        amount: BigDecimal,
        fromCurrency: String,
        toCurrency: String
    ): ConversionResult? {
        val rate = getExchangeRate(fromCurrency, toCurrency) ?: return null
        val convertedAmount = amount.multiply(rate)
        
        val result = ConversionResult(
            fromCurrency = fromCurrency,
            toCurrency = toCurrency,
            fromAmount = amount,
            toAmount = convertedAmount,
            rate = rate,
            timestamp = System.currentTimeMillis()
        )
        
        // Add to recent conversions
        addToRecentConversions(result)
        
        return result
    }
    
    private fun addToRecentConversions(result: ConversionResult) {
        val currentConversions = _recentConversions.value.toMutableList()
        
        // Remove any existing conversion with same currencies
        currentConversions.removeAll { 
            it.fromCurrency == result.fromCurrency && 
            it.toCurrency == result.toCurrency 
        }
        
        // Add new conversion at the beginning
        currentConversions.add(0, result)
        
        // Keep only last 10 conversions
        if (currentConversions.size > 10) {
            currentConversions.removeAt(currentConversions.size - 1)
        }
        
        _recentConversions.value = currentConversions
    }
    
    override suspend fun clearRecentConversions() {
        _recentConversions.value = emptyList()
    }
} 