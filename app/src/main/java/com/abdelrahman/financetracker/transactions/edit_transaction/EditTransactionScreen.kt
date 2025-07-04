package com.abdelrahman.financetracker.transactions.edit_transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abdelrahman.financetracker.domain.model.TransactionType
import com.abdelrahman.financetracker.transactions.components.*
import com.abdelrahman.financetracker.transactions.edit_transaction.intent.EditTransactionIntent
import com.abdelrahman.financetracker.transactions.edit_transaction.state.EditTransactionState

@Composable
fun EditTransactionsScreen(
    transactionId: String,
    onNavigateBack: () -> Unit = {},
    viewModel: EditTransactionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    // Load transaction when screen is created
    LaunchedEffect(transactionId) {
        viewModel.handleIntent(EditTransactionIntent.LoadTransaction(transactionId))
    }
    
    // Handle side effects
    LaunchedEffect(state.isUpdateSuccess) {
        if (state.isUpdateSuccess) {
            onNavigateBack()
        }
    }
    
    LaunchedEffect(state.isDeleteSuccess) {
        if (state.isDeleteSuccess) {
            onNavigateBack()
        }
    }

    EditTransactionsScreenContent(
        state = state,
        onNavigateBack = onNavigateBack,
        onIntent = viewModel::handleIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTransactionsScreenContent(
    state: EditTransactionState,
    onNavigateBack: () -> Unit,
    onIntent: (EditTransactionIntent) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Edit Transaction",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        color = Color(0xFF1A1A1A)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier
                            .padding(8.dp)
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
                actions = {
                    // Delete button in top bar - only show if transaction is loaded
                    if (state.originalTransaction != null && !state.isLoading) {
                        IconButton(
                            onClick = { onIntent(EditTransactionIntent.ToggleDeleteDialog(true)) },
                            modifier = Modifier
                                .padding(8.dp)
                                .size(40.dp)
                                .background(Color(0xFFFEF2F2), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color(0xFFEF4444)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFAFAFA)
                )
            )
        },
        containerColor = Color(0xFFFAFAFA)
    ) { paddingValues ->
        when {
            state.isLoading -> {
                // Loading state
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFF007AFF)
                        )
                        Text(
                            text = "Loading transaction...",
                            color = Color(0xFF6B7280),
                            fontSize = 16.sp
                        )
                    }
                }
            }
            
            state.error != null -> {
                // Error state
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFEF2F2)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "Error",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFEF4444)
                            )
                            Text(
                                text = state.error,
                                fontSize = 16.sp,
                                color = Color(0xFF6B7280),
                                textAlign = TextAlign.Center
                            )
                            Button(
                                onClick = onNavigateBack,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF007AFF)
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Go Back")
                            }
                        }
                    }
                }
            }
            
            state.originalTransaction != null -> {
                // Transaction loaded - show edit form
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
                onTypeSelected = { onIntent(EditTransactionIntent.SelectType(it)) }
            )

            // Category Selection
            CategorySelector(
                selectedCategory = state.selectedCategory,
                onCategorySelected = { onIntent(EditTransactionIntent.SelectCategory(it)) },
                categories = state.categories,
                showDropdown = state.showCategoryDropdown,
                onDropdownToggle = { onIntent(EditTransactionIntent.ToggleDropdown(it)) }
            )

            // Amount Input
            AmountInput(
                amount = state.amount,
                onAmountChanged = { onIntent(EditTransactionIntent.UpdateAmount(it)) }
            )

            // Date Input
            DateInput(
                date = state.date,
                onDateChanged = { onIntent(EditTransactionIntent.UpdateDate(it)) }
            )

            // Notes Input
            NotesInput(
                notes = state.notes,
                onNotesChanged = { onIntent(EditTransactionIntent.UpdateNotes(it)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Delete Button
                OutlinedButton(
                    onClick = { onIntent(EditTransactionIntent.ToggleDeleteDialog(true)) },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFEF4444)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Delete",
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Save Button
                Button(
                    onClick = { onIntent(EditTransactionIntent.SaveTransaction) },
                    enabled = state.selectedCategory.isNotEmpty() && state.amount.isNotEmpty() && state.date.isNotEmpty(),
                    modifier = Modifier
                        .weight(2f)
                        .height(56.dp)
                        .shadow(8.dp, RoundedCornerShape(16.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF007AFF),
                        disabledContainerColor = Color(0xFFE5E7EB)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Save Changes",
                        fontWeight = FontWeight.SemiBold
                    )
                }
                    }
                }
            }
        }

        // Delete Confirmation Dialog
        if (state.showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { onIntent(EditTransactionIntent.ToggleDeleteDialog(false)) },
                title = {
                    Text(
                        text = "Delete Transaction",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A1A)
                    )
                },
                text = {
                    Text(
                        text = "Are you sure you want to delete this transaction? This action cannot be undone.",
                        color = Color(0xFF6B7280)
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = { onIntent(EditTransactionIntent.DeleteTransaction) },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Color(0xFFEF4444)
                        )
                    ) {
                        Text("Delete", fontWeight = FontWeight.SemiBold)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { onIntent(EditTransactionIntent.ToggleDeleteDialog(false)) },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Color(0xFF6B7280)
                        )
                    ) {
                        Text("Cancel", fontWeight = FontWeight.Medium)
                    }
                },
                containerColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}

@Preview
@Composable
fun EditTransactionsScreenContentPreview() {
    MaterialTheme {
        EditTransactionsScreenContent(
            state = EditTransactionState(
                selectedType = TransactionType.EXPENSE,
                selectedCategory = "Groceries",
                amount = "125.50",
                date = "15-12-2023",
                notes = "Weekly shopping",
                showCategoryDropdown = false,
                showDeleteDialog = false,
                categories = listOf("Groceries", "Transportation", "Entertainment"),
                originalTransaction = null // For preview, we'll show empty state
            ),
            onNavigateBack = {},
            onIntent = {}
        )
    }
}