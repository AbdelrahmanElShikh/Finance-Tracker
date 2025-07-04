package com.abdelrahman.financetracker.currencyconverter.domain.usecase

import com.abdelrahman.financetracker.currencyconverter.domain.repository.ConversionResult
import com.abdelrahman.financetracker.currencyconverter.domain.repository.CurrencyRepository
import java.math.BigDecimal
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(
        fromCurrency: String,
        toCurrency: String,
        amount: BigDecimal
    ): ConversionResult? {
        return repository.convertCurrency(amount, fromCurrency, toCurrency)
    }
} 