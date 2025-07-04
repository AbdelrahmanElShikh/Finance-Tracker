package com.abdelrahman.financetracker.transactions.list_transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abdelrahman.financetracker.transactions.components.TransactionItem
import com.abdelrahman.financetracker.transactions.components.TransactionSummary
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import com.abdelrahman.financetracker.transactions.list_transactions.intent.ListTransactionsIntent
import com.abdelrahman.financetracker.transactions.list_transactions.state.ListTransactionsState
import com.abdelrahman.financetracker.transactions.mockTransactions.getFakeTransactions

@Composable
fun ListTransactionsScreen(
    onNavigateToAdd: () -> Unit,
    onNavigateToEdit: (String) -> Unit,
    onNavigateToCurrencyConverter: () -> Unit,
    viewModel: ListTransactionsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ListTransactionsScreenContent(
        state = state,
        onNavigateToAdd = onNavigateToAdd,
        onNavigateToEdit = onNavigateToEdit,
        onNavigateToCurrencyConverter = onNavigateToCurrencyConverter
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListTransactionsScreenContent(
    state: ListTransactionsState,
    onNavigateToAdd: () -> Unit,
    onNavigateToEdit: (String) -> Unit,
    onNavigateToCurrencyConverter: () -> Unit,
    ) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Finance Overview",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp,
                        color = Color(0xFF1A1A1A)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFAFAFA)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAdd,
                containerColor = Color(0xFF007AFF),
                contentColor = Color.White,
                modifier = Modifier
                    .size(56.dp)
                    .shadow(12.dp, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Transaction",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        containerColor = Color(0xFFFAFAFA)
    ) { paddingValues ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    // Summary Cards
                    TransactionSummary(state.transactions,onNavigateToCurrencyConverter)
                }

                item {
                    // Section Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Recent Activity",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A1A1A),
                            fontSize = 20.sp
                        )
                        Text(
                            text = "View All",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF007AFF),
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.clickable { }
                        )
                    }
                }

                if (state.transactions.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(48.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "No transactions yet",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6B7280)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Add your first transaction to get started",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF9CA3AF)
                                )
                            }
                        }
                    }
                } else {
                    items(state.transactions) { transaction ->
                        TransactionItem(
                            transaction = transaction,
                            onClick = { onNavigateToEdit(transaction.id) }
                        )
                    }
                }
            }
        }
    }
}


// Preview methods for different states
@Preview
@Composable
fun ListTransactionsScreenContentWithDataPreview() {
    MaterialTheme {
        ListTransactionsScreenContent(
            state = ListTransactionsState(
                transactions = getFakeTransactions().take(3),
                isLoading = false
            ),
            onNavigateToAdd = {},
            onNavigateToEdit = {},
            onNavigateToCurrencyConverter = {}
        )
    }
}
