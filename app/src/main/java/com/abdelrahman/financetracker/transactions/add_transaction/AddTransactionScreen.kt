package com.abdelrahman.financetracker.transactions.add_transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abdelrahman.financetracker.domain.model.TransactionType
import com.abdelrahman.financetracker.transactions.add_transaction.intent.AddTransactionIntent
import com.abdelrahman.financetracker.transactions.add_transaction.state.AddTransactionState
import com.abdelrahman.financetracker.transactions.components.*

@Composable
fun AddTransactionsScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: AddTransactionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    // Handle side effects
    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onNavigateBack()
        }
    }

    AddTransactionsScreenContent(
        state = state,
        onNavigateBack = onNavigateBack,
        onIntent = viewModel::handleIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionsScreenContent(
    state: AddTransactionState,
    onNavigateBack: () -> Unit,
    onIntent: (AddTransactionIntent) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Add Transaction",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        color = Color(0xFF1A1A1A)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color(0xFFF5F5F5), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF1A1A1A)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFAFAFA)
                )
            )
        },
        containerColor = Color(0xFFFAFAFA)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Transaction Type Selection
            TransactionTypeSelector(
                selectedType = state.selectedType,
                onTypeSelected = { onIntent(AddTransactionIntent.SelectType(it)) }
            )

            // Category Selection
            CategorySelector(
                selectedCategory = state.selectedCategory,
                onCategorySelected = { onIntent(AddTransactionIntent.SelectCategory(it)) },
                categories = state.categories,
                showDropdown = state.showCategoryDropdown,
                onDropdownToggle = { onIntent(AddTransactionIntent.ToggleDropdown(it)) }
            )

            // Amount Input
            AmountInput(
                amount = state.amount,
                onAmountChanged = { onIntent(AddTransactionIntent.UpdateAmount(it)) }
            )

            // Date Input
            DateInput(
                date = state.date,
                onDateChanged = { onIntent(AddTransactionIntent.UpdateDate(it)) }
            )

            // Notes Input
            NotesInput(
                notes = state.notes,
                onNotesChanged = { onIntent(AddTransactionIntent.UpdateNotes(it)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Save Button
            SaveButton(
                enabled = state.selectedCategory.isNotEmpty() && state.amount.isNotEmpty() && state.date.isNotEmpty(),
                onSave = { onIntent(AddTransactionIntent.SaveTransaction) }
            )
        }
    }
}


@Preview
@Composable
fun AddTransactionsScreenContentPreview() {
    MaterialTheme {
        AddTransactionsScreenContent(
            state = AddTransactionState(
                selectedType = TransactionType.EXPENSE,
                selectedCategory = "Transportation",
                amount = "45",
                date = "",
                notes = "",
                showCategoryDropdown = false,
                categories = listOf("Transportation", "Food", "Shopping")
            ),
            onNavigateBack = {},
            onIntent = {}
        )
    }
}

