package com.abdelrahman.financetracker.currencyconverter.presentation.currencyConverter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.financetracker.currencyconverter.domain.model.Currency
import com.abdelrahman.financetracker.currencyconverter.domain.usecase.ConvertCurrencyUseCase
import com.abdelrahman.financetracker.currencyconverter.domain.usecase.GetSupportedCurrenciesUseCase
import com.abdelrahman.financetracker.currencyconverter.presentation.currencyConverter.intnet.CurrencyConverterIntent
import com.abdelrahman.financetracker.currencyconverter.presentation.currencyConverter.state.CurrencyConverterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject


@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val getSupportedCurrenciesUseCase: GetSupportedCurrenciesUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val currencyRepository: com.abdelrahman.financetracker.currencyconverter.domain.repository.CurrencyRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CurrencyConverterState())
    val state: StateFlow<CurrencyConverterState> = _state.asStateFlow()

    init {
        handleIntent(CurrencyConverterIntent.LoadCurrencies)

        // Observe recent conversions from repository
        viewModelScope.launch {
            currencyRepository.recentConversions.collect { recentConversions ->
                _state.value = _state.value.copy(recentConversions = recentConversions)
            }
        }
    }

    fun handleIntent(intent: CurrencyConverterIntent) {
        when (intent) {
            CurrencyConverterIntent.LoadCurrencies -> loadCurrencies()
            is CurrencyConverterIntent.SelectFromCurrency -> selectFromCurrency(intent.currency)
            is CurrencyConverterIntent.SelectToCurrency -> selectToCurrency(intent.currency)
            is CurrencyConverterIntent.UpdateAmount -> updateAmount(intent.amount)
            is CurrencyConverterIntent.ConvertCurrency -> convertCurrency()
            is CurrencyConverterIntent.SwapCurrencies -> swapCurrencies()
            is CurrencyConverterIntent.ToggleFromCurrencySelector -> toggleFromCurrencySelector(
                intent.show
            )

            is CurrencyConverterIntent.ToggleToCurrencySelector -> toggleToCurrencySelector(intent.show)
            is CurrencyConverterIntent.ClearError -> clearError()
            is CurrencyConverterIntent.ClearResult -> clearResult()
        }
    }

    private fun loadCurrencies() {
        viewModelScope.launch {
            val currencies = getSupportedCurrenciesUseCase()
            _state.value = _state.value.copy(
                supportedCurrencies = currencies,
                fromCurrency = _state.value.fromCurrency
                    ?: currencies.firstOrNull { it.code == "USD" },
                toCurrency = _state.value.toCurrency ?: currencies.firstOrNull { it.code == "EUR" }
            )
        }
    }

    private fun selectFromCurrency(currency: Currency) {
        _state.value = _state.value.copy(
            fromCurrency = currency,
            showFromCurrencySelector = false,
            conversionResult = null
        )
    }

    private fun selectToCurrency(currency: Currency) {
        _state.value = _state.value.copy(
            toCurrency = currency,
            showToCurrencySelector = false,
            conversionResult = null
        )
    }

    private fun updateAmount(amount: String) {
        _state.value = _state.value.copy(
            amount = amount,
            conversionResult = null
        )
    }

    private fun convertCurrency() {
        val currentState = _state.value
        val fromCurrency = currentState.fromCurrency
        val toCurrency = currentState.toCurrency
        val amountBigDecimal = try {
            BigDecimal(currentState.amount)
        } catch (e: NumberFormatException) {
            null
        }

        if (fromCurrency == null || toCurrency == null) {
            _state.value = currentState.copy(error = "Please select both currencies")
            return
        }

        if (amountBigDecimal == null || amountBigDecimal <= BigDecimal.ZERO) {
            _state.value = currentState.copy(error = "Please enter a valid amount")
            return
        }

        viewModelScope.launch {
            _state.value = currentState.copy(isLoading = true, error = null)

            val result = convertCurrencyUseCase(
                fromCurrency = fromCurrency.code,
                toCurrency = toCurrency.code,
                amount = amountBigDecimal
            )

            if (result != null) {
                _state.value = currentState.copy(
                    isLoading = false,
                    conversionResult = result,
                    error = null
                )
            } else {
                _state.value = currentState.copy(
                    isLoading = false,
                    error = "Conversion failed - unable to get exchange rate"
                )
            }
        }
    }

    private fun swapCurrencies() {
        val currentState = _state.value
        _state.value = currentState.copy(
            fromCurrency = currentState.toCurrency,
            toCurrency = currentState.fromCurrency,
            conversionResult = null
        )
    }

    private fun toggleFromCurrencySelector(show: Boolean) {
        _state.value = _state.value.copy(
            showFromCurrencySelector = show,
            showToCurrencySelector = if (show) false else _state.value.showToCurrencySelector
        )
    }

    private fun toggleToCurrencySelector(show: Boolean) {
        _state.value = _state.value.copy(
            showToCurrencySelector = show,
            showFromCurrencySelector = if (show) false else _state.value.showFromCurrencySelector
        )
    }

    private fun clearError() {
        _state.value = _state.value.copy(error = null)
    }

    private fun clearResult() {
        _state.value = _state.value.copy(conversionResult = null)
    }
} 