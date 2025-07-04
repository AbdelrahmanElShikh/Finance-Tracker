package com.abdelrahman.financetracker.currencyconverter.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.financetracker.currencyconverter.domain.plugin.CurrencyPluginRegistry
import com.abdelrahman.financetracker.currencyconverter.domain.usecase.GetAvailableProvidersUseCase
import com.abdelrahman.financetracker.currencyconverter.domain.usecase.SetActiveProviderUseCase
import com.abdelrahman.financetracker.currencyconverter.presentation.settings.intnet.ProviderSettingsIntent
import com.abdelrahman.financetracker.currencyconverter.presentation.settings.state.ProviderSettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProviderSettingsViewModel @Inject constructor(
    private val getAvailableProvidersUseCase: GetAvailableProvidersUseCase,
    private val setActiveProviderUseCase: SetActiveProviderUseCase,
    private val pluginRegistry: CurrencyPluginRegistry
) : ViewModel() {

    private val _state = MutableStateFlow(ProviderSettingsState())
    val state: StateFlow<ProviderSettingsState> = _state.asStateFlow()

    init {
        handleIntent(ProviderSettingsIntent.LoadProviders)
    }

    fun handleIntent(intent: ProviderSettingsIntent) {
        when (intent) {
            is ProviderSettingsIntent.LoadProviders -> loadProviders()
            is ProviderSettingsIntent.SelectProvider -> selectProvider(intent.providerId)
            is ProviderSettingsIntent.ClearError -> clearError()
        }
    }

    private fun loadProviders() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            try {
                val providers = getAvailableProvidersUseCase()
                val activeProviderId = pluginRegistry.getActiveProviderId()

                _state.value = _state.value.copy(
                    availableProviders = providers,
                    activeProviderId = activeProviderId,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load providers"
                )
            }
        }
    }

    private fun selectProvider(providerId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            try {
                setActiveProviderUseCase(providerId)

                _state.value = _state.value.copy(
                    activeProviderId = providerId,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to select provider"
                )
            }
        }
    }

    private fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
} 