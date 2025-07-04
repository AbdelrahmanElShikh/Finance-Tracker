package com.abdelrahman.financetracker.transactions.edit_transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.financetracker.domain.model.TransactionType
import com.abdelrahman.financetracker.domain.usecase.DeleteTransactionUseCase
import com.abdelrahman.financetracker.domain.usecase.GetTransactionByIdUseCase
import com.abdelrahman.financetracker.domain.usecase.UpdateTransactionUseCase
import com.abdelrahman.financetracker.transactions.edit_transaction.intent.EditTransactionIntent
import com.abdelrahman.financetracker.transactions.edit_transaction.state.EditTransactionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditTransactionViewModel @Inject constructor(
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditTransactionState())
    val state: StateFlow<EditTransactionState> = _state.asStateFlow()

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

    fun handleIntent(intent: EditTransactionIntent) {
        when (intent) {
            is EditTransactionIntent.LoadTransaction -> loadTransaction(intent.transactionId)
            is EditTransactionIntent.SelectType -> selectType(intent.type)
            is EditTransactionIntent.SelectCategory -> selectCategory(intent.category)
            is EditTransactionIntent.UpdateAmount -> updateAmount(intent.amount)
            is EditTransactionIntent.UpdateDate -> updateDate(intent.date)
            is EditTransactionIntent.UpdateNotes -> updateNotes(intent.notes)
            is EditTransactionIntent.ToggleDropdown -> toggleDropdown(intent.show)
            is EditTransactionIntent.ToggleDeleteDialog -> toggleDeleteDialog(intent.show)
            is EditTransactionIntent.SaveTransaction -> saveTransaction()
            is EditTransactionIntent.DeleteTransaction -> deleteTransaction()
            is EditTransactionIntent.ClearError -> clearError()
        }
    }

    private fun loadTransaction(transactionId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val transaction = getTransactionByIdUseCase(transactionId)
                if (transaction != null) {
                    _state.value = _state.value.copy(
                        originalTransaction = transaction,
                        selectedType = transaction.type,
                        selectedCategory = transaction.category,
                        amount = transaction.amount.toString(),
                        date = transaction.date,
                        notes = transaction.notes ?: "",
                        isLoading = false
                    )
                    updateCategoriesForType(transaction.type)
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Transaction not found"
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load transaction"
                )
            }
        }
    }

    private fun selectType(type: TransactionType) {
        val currentState = _state.value
        val categories = when (type) {
            TransactionType.INCOME -> incomeCategories
            TransactionType.EXPENSE -> expenseCategories
        }

        // Reset category if it doesn't exist in the new type
        val newCategory = if (currentState.selectedCategory in categories) {
            currentState.selectedCategory
        } else {
            ""
        }

        _state.value = currentState.copy(
            selectedType = type,
            selectedCategory = newCategory,
            showCategoryDropdown = false,
            categories = categories
        )
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

    private fun toggleDeleteDialog(show: Boolean) {
        _state.value = _state.value.copy(showDeleteDialog = show)
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
        val originalTransaction = currentState.originalTransaction

        if (originalTransaction == null) {
            _state.value = currentState.copy(error = "No transaction to update")
            return
        }

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
                val updatedTransaction = originalTransaction.copy(
                    type = currentState.selectedType,
                    category = currentState.selectedCategory,
                    amount = amount,
                    date = currentState.date,
                    notes = currentState.notes.ifBlank { null }
                )

                updateTransactionUseCase(updatedTransaction)
                _state.value = currentState.copy(
                    isLoading = false,
                    isUpdateSuccess = true,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = currentState.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to update transaction"
                )
            }
        }
    }

    private fun deleteTransaction() {
        val currentState = _state.value
        val originalTransaction = currentState.originalTransaction

        if (originalTransaction == null) {
            _state.value = currentState.copy(error = "No transaction to delete")
            return
        }

        viewModelScope.launch {
            _state.value = currentState.copy(isLoading = true, error = null)
            try {
                deleteTransactionUseCase(originalTransaction)
                _state.value = currentState.copy(
                    isLoading = false,
                    isDeleteSuccess = true,
                    showDeleteDialog = false,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = currentState.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to delete transaction"
                )
            }
        }
    }

    private fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}
