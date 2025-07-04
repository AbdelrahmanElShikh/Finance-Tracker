package com.abdelrahman.financetracker.currencyconverter.presentation.currencyConverter.intnet

import com.abdelrahman.financetracker.currencyconverter.domain.model.Currency

sealed class CurrencyConverterIntent {
    data object LoadCurrencies : CurrencyConverterIntent()
    data class SelectFromCurrency(val currency: Currency) : CurrencyConverterIntent()
    data class SelectToCurrency(val currency: Currency) : CurrencyConverterIntent()
    data class UpdateAmount(val amount: String) : CurrencyConverterIntent()
    data object ConvertCurrency : CurrencyConverterIntent()
    data object SwapCurrencies : CurrencyConverterIntent()
    data class ToggleFromCurrencySelector(val show: Boolean) : CurrencyConverterIntent()
    data class ToggleToCurrencySelector(val show: Boolean) : CurrencyConverterIntent()
    data object ClearError : CurrencyConverterIntent()
    data object ClearResult : CurrencyConverterIntent()
}