package com.abdelrahman.financetracker.currencyconverter.domain.usecase

import com.abdelrahman.financetracker.currencyconverter.domain.model.ExchangeRate
import com.abdelrahman.financetracker.currencyconverter.domain.repository.CurrencyRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue

class ConvertCurrencyUseCaseTest {

    @Mock
    private lateinit var repository: CurrencyRepository

    private lateinit var convertCurrencyUseCase: ConvertCurrencyUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        convertCurrencyUseCase = ConvertCurrencyUseCase(repository)
    }

    @Test
    fun `invoke should return successful conversion result`() = runTest {
        // Given
        val fromCurrency = "USD"
        val toCurrency = "EUR"
        val amount = 100.0
        val exchangeRate = ExchangeRate(
            fromCurrency = fromCurrency,
            toCurrency = toCurrency,
            rate = 0.85,
            timestamp = System.currentTimeMillis()
        )

        whenever(repository.getExchangeRate(fromCurrency, toCurrency))
            .thenReturn(Result.success(exchangeRate))

        // When
        val result = convertCurrencyUseCase(fromCurrency, toCurrency, amount)

        // Then
        assertTrue(result.isSuccess)
        val conversionResult = result.getOrThrow()
        assertEquals(fromCurrency, conversionResult.fromCurrency)
        assertEquals(toCurrency, conversionResult.toCurrency)
        assertEquals(amount, conversionResult.fromAmount)
        assertEquals(85.0, conversionResult.toAmount)
        assertEquals(0.85, conversionResult.rate)
    }

    @Test
    fun `invoke should return failure when repository fails`() = runTest {
        // Given
        val fromCurrency = "USD"
        val toCurrency = "EUR"
        val amount = 100.0
        val exception = Exception("Network error")

        whenever(repository.getExchangeRate(fromCurrency, toCurrency))
            .thenReturn(Result.failure(exception))

        // When
        val result = convertCurrencyUseCase(fromCurrency, toCurrency, amount)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
} 