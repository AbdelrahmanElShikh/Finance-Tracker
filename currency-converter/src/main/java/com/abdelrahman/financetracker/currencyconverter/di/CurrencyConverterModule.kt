package com.abdelrahman.financetracker.currencyconverter.di

import com.abdelrahman.financetracker.currencyconverter.data.plugin.ExchangeRateApiPlugin
import com.abdelrahman.financetracker.currencyconverter.data.plugin.ExchangeRateApiService
import com.abdelrahman.financetracker.currencyconverter.data.plugin.MockCurrencyPlugin
import com.abdelrahman.financetracker.currencyconverter.data.repository.CurrencyRepositoryImpl
import com.abdelrahman.financetracker.currencyconverter.domain.plugin.CurrencyPluginRegistry
import com.abdelrahman.financetracker.currencyconverter.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CurrencyConverterModule {
    
    @Provides
    @Singleton
    fun provideExchangeRateRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.exchangerate-api.com/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    @Provides
    @Singleton
    fun provideExchangeRateApiService(retrofit: Retrofit): ExchangeRateApiService {
        return retrofit.create(ExchangeRateApiService::class.java)
    }
    
    @Provides
    @Singleton
    fun provideCurrencyPluginRegistry(
        mockPlugin: MockCurrencyPlugin,
        exchangeRateApiPlugin: ExchangeRateApiPlugin
    ): CurrencyPluginRegistry {
        val registry = CurrencyPluginRegistry()
        
        // Register plugins
        CoroutineScope(Dispatchers.IO).launch {
            registry.registerPlugin(mockPlugin)
            registry.registerPlugin(exchangeRateApiPlugin)
        }
        
        return registry
    }
    
    @Provides
    @Singleton
    fun provideCurrencyRepository(
        pluginRegistry: CurrencyPluginRegistry
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(pluginRegistry)
    }
} 