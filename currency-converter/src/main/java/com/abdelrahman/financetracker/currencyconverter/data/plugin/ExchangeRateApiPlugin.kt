package com.abdelrahman.financetracker.currencyconverter.data.plugin

import com.abdelrahman.financetracker.currencyconverter.domain.plugin.CurrencyProviderPlugin
import retrofit2.http.GET
import retrofit2.http.Path
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Simple ExchangeRate-API provider plugin
 */
@Singleton
class ExchangeRateApiPlugin @Inject constructor(
    private val apiService: ExchangeRateApiService
) : CurrencyProviderPlugin {
    
    override val id: String = "exchangerate-api"
    override val displayName: String = "ExchangeRate-API"
    
    override suspend fun getRate(fromCurrency: String, toCurrency: String): BigDecimal? {
        return try {
            val response = apiService.getExchangeRates(fromCurrency)
            val rate = response.rates[toCurrency] ?: return null
            BigDecimal(rate.toString())
        } catch (e: Exception) {
            null
        }
    }
}

/**
 * API service interface for ExchangeRate-API
 */
interface ExchangeRateApiService {
    @GET("latest/{base}")
    suspend fun getExchangeRates(@Path("base") baseCurrency: String): ExchangeRateApiResponse
}

/**
 * Response model for ExchangeRate-API
 */
data class ExchangeRateApiResponse(
    val base: String,
    val rates: Map<String, Double>
) 