package com.abdelrahman.financetracker.transactions.list_transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.financetracker.domain.usecase.GetAllTransactionsUseCase
import com.abdelrahman.financetracker.transactions.list_transactions.intent.ListTransactionsIntent
import com.abdelrahman.financetracker.transactions.list_transactions.state.ListTransactionsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject




@HiltViewModel
class ListTransactionsViewModel @Inject constructor(
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase
) : ViewModel() {
    
    private val _state = MutableStateFlow(ListTransactionsState())
    val state: StateFlow<ListTransactionsState> = _state.asStateFlow()
    
    init {
        handleIntent(ListTransactionsIntent.LoadTransactions)
    }
    
    fun handleIntent(intent: ListTransactionsIntent) {
        when (intent) {
            ListTransactionsIntent.LoadTransactions -> loadTransactions()
            ListTransactionsIntent.RefreshTransactions -> refreshTransactions()
        }
    }
    
    private fun loadTransactions() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                getAllTransactionsUseCase().collect { transactionList ->
                    _state.value = _state.value.copy(
                        transactions = transactionList,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }
    
    private fun refreshTransactions() {
        loadTransactions()
    }
} 