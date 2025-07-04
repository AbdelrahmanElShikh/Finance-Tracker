package com.abdelrahman.financetracker.transactions.add_transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.financetracker.domain.model.Transaction
import com.abdelrahman.financetracker.domain.model.TransactionType
import com.abdelrahman.financetracker.domain.usecase.AddTransactionUseCase
import com.abdelrahman.financetracker.transactions.add_transaction.intent.AddTransactionIntent
import com.abdelrahman.financetracker.transactions.add_transaction.state.AddTransactionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val addTransactionUseCase: AddTransactionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddTransactionState())
    val state: StateFlow<AddTransactionState> = _state.asStateFlow()

    private val incomeCategories = listOf(
        "Salary", "Freelance", "Investment", "Side Business", "Rental Income",
        "Dividends", "Interest", "Bonus", "Commission", "Gifts Received",
        "Tax Refund", "Business Income", "Other Income"
    )

    private val expenseCategories = listOf(
        "Groceries", "Restaurants", "Fast Food", "Coffee & Snacks",
        "Rent/Mortgage", "Utilities", "Internet & Phone", "Insurance",
        "Car Payment", "Gas", "Public Transport", "Uber/Taxi",
        "Shopping", "Clothing", "Electronics", "Home & Garden",
        "Entertainment", "Movies", "Sports", "Hobbies",
        "Health", "Pharmacy", "Doctor", "Gym",
        "Education", "Books", "Courses", "Travel",
        "Hotels", "Flights", "Subscriptions", "Banking Fees",
        "Charity", "Gifts Given", "Other Expenses"
    )

    init {
        updateCategoriesForType(TransactionType.EXPENSE)
    }

    fun handleIntent(intent: AddTransactionIntent) {
        when (intent) {
            is AddTransactionIntent.SelectType -> selectType(intent.type)
            is AddTransactionIntent.SelectCategory -> selectCategory(intent.category)
            is AddTransactionIntent.UpdateAmount -> updateAmount(intent.amount)
            is AddTransactionIntent.UpdateDate -> updateDate(intent.date)
            is AddTransactionIntent.UpdateNotes -> updateNotes(intent.notes)
            is AddTransactionIntent.ToggleDropdown -> toggleDropdown(intent.show)
            is AddTransactionIntent.SaveTransaction -> saveTransaction()
            is AddTransactionIntent.ResetForm -> resetForm()
            is AddTransactionIntent.ClearError -> clearError()
        }
    }

    private fun selectType(type: TransactionType) {
        _state.value = _state.value.copy(
            selectedType = type,
            selectedCategory = "", // Reset category when type changes
            showCategoryDropdown = false
        )
        updateCategoriesForType(type)
    }

    private fun selectCategory(category: String) {
        _state.value = _state.value.copy(
            selectedCategory = category,
            showCategoryDropdown = false
        )
    }

    private fun updateAmount(amount: String) {
        _state.value = _state.value.copy(amount = amount)
    }

    private fun updateDate(date: String) {
        _state.value = _state.value.copy(date = date)
    }

    private fun updateNotes(notes: String) {
        _state.value = _state.value.copy(notes = notes)
    }

    private fun toggleDropdown(show: Boolean) {
        _state.value = _state.value.copy(showCategoryDropdown = show)
    }

    private fun updateCategoriesForType(type: TransactionType) {
        val categories = when (type) {
            TransactionType.INCOME -> incomeCategories
            TransactionType.EXPENSE -> expenseCategories
        }
        _state.value = _state.value.copy(categories = categories)
    }

    private fun saveTransaction() {
        val currentState = _state.value

        // Validate form
        if (currentState.selectedCategory.isEmpty()) {
            _state.value = currentState.copy(error = "Please select a category")
            return
        }

        if (currentState.amount.isEmpty()) {
            _state.value = currentState.copy(error = "Please enter an amount")
            return
        }

        if (currentState.date.isEmpty()) {
            _state.value = currentState.copy(error = "Please select a date")
            return
        }

        val amount = currentState.amount.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            _state.value = currentState.copy(error = "Please enter a valid amount")
            return
        }

        viewModelScope.launch {
            _state.value = currentState.copy(isLoading = true, error = null)
            try {
                val transaction = Transaction(
                    id = UUID.randomUUID().toString(),
                    type = currentState.selectedType,
                    category = currentState.selectedCategory,
                    amount = amount,
                    date = currentState.date,
                    notes = currentState.notes.ifBlank { null }
                )

                addTransactionUseCase(transaction)
                _state.value = currentState.copy(
                    isLoading = false,
                    isSuccess = true,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = currentState.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to save transaction"
                )
            }
        }
    }

    private fun resetForm() {
        _state.value = AddTransactionState()
        updateCategoriesForType(TransactionType.EXPENSE)
    }

    private fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
} 