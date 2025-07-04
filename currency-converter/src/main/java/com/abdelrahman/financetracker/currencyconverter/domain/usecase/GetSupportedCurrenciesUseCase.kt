package com.abdelrahman.financetracker.currencyconverter.domain.usecase

import com.abdelrahman.financetracker.currencyconverter.domain.model.Currency
import com.abdelrahman.financetracker.currencyconverter.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetSupportedCurrenciesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(): List<Currency> {
        return repository.getSupportedCurrencies()
    }
} 