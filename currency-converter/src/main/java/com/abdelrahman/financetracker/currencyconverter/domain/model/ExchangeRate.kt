package com.abdelrahman.financetracker.currencyconverter.domain.model

data class ExchangeRate(
    val fromCurrency: String,
    val toCurrency: String,
    val rate: Double,
    val timestamp: Long = System.currentTimeMillis()
)

data class ExchangeRateResponse(
    val baseCurrency: String,
    val rates: Map<String, Double>,
    val timestamp: Long = System.currentTimeMillis()
)

 