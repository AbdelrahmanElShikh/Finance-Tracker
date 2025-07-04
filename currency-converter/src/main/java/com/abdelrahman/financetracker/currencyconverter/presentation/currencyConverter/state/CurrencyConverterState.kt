package com.abdelrahman.financetracker.currencyconverter.presentation.currencyConverter.state

import com.abdelrahman.financetracker.currencyconverter.domain.model.Currency
import com.abdelrahman.financetracker.currencyconverter.domain.repository.ConversionResult

data class CurrencyConverterState(
    val supportedCurrencies: List<Currency> = emptyList(),
    val fromCurrency: Currency? = null,
    val toCurrency: Currency? = null,
    val amount: String = "",
    val conversionResult: ConversionResult? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val showFromCurrencySelector: Boolean = false,
    val showToCurrencySelector: Boolean = false,
    val recentConversions: List<ConversionResult> = emptyList()
)