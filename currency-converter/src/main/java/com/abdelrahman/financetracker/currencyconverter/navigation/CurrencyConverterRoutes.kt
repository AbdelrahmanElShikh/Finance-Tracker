package com.abdelrahman.financetracker.currencyconverter.navigation

import kotlinx.serialization.Serializable

sealed interface CurrencyConverterRoute {

    @Serializable
    data object CurrencyConverter : CurrencyConverterRoute

    @Serializable
    data object CurrencySettings : CurrencyConverterRoute

}
