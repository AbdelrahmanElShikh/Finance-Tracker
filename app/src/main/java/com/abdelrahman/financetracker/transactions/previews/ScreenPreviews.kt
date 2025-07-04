package com.abdelrahman.financetracker.transactions.previews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdelrahman.financetracker.domain.model.Transaction
import com.abdelrahman.financetracker.domain.model.TransactionType
import com.abdelrahman.financetracker.transactions.components.*

// Sample data for previews
val previewTransactions = listOf(
    Transaction(
        id = "1",
        type = TransactionType.INCOME,
        category = "Salary",
        amount = 5000.0,
        date = "01-12-2023",
        notes = "Monthly salary payment"
    ),
    Transaction(
        id = "2",
        type = TransactionType.EXPENSE,
        category = "Groceries",
        amount = 125.50,
        date = "15-12-2023",
        notes = "Weekly grocery shopping at Walmart"
    ),
    Transaction(
        id = "3",
        type = TransactionType.EXPENSE,
        category = "Gas",
        amount = 45.0,
        date = "14-12-2023",
        notes = null
    ),
    Transaction(
        id = "4",
        type = TransactionType.INCOME,
        category = "Freelance",
        amount = 750.0,
        date = "12-12-2023",
        notes = "Web design project payment"
    ),
    Transaction(
        id = "5",
        type = TransactionType.EXPENSE,
        category = "Entertainment",
        amount = 67.80,
        date = "10-12-2023",
        notes = "Dinner at Italian restaurant"
    )
)

// ListTransactionsScreen Preview Components
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListTransactionsScreenContent(
    transactions: List<Transaction>,
    isLoading: Boolean = false,
    onNavigateToAdd: () -> Unit = {},
    onNavigateToEdit: (String) -> Unit = {}
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
        if (isLoading) {
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
                    TransactionSummary(transactions)
                }

                item {
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

                if (transactions.isEmpty()) {
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
                    items(transactions) { transaction ->
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

// Preview Methods
@Preview(name = "List - With Data", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun ListTransactionsScreenWithDataPreview() {
    MaterialTheme {
        ListTransactionsScreenContent(
            transactions = previewTransactions,
            isLoading = false
        )
    }
}

@Preview(name = "List - Empty State", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun ListTransactionsScreenEmptyStatePreview() {
    MaterialTheme {
        ListTransactionsScreenContent(
            transactions = emptyList(),
            isLoading = false
        )
    }
}

@Preview(name = "List - Loading", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun ListTransactionsScreenLoadingStatePreview() {
    MaterialTheme {
        ListTransactionsScreenContent(
            transactions = emptyList(),
            isLoading = true
        )
    }
}

@Preview(name = "List - Single Transaction", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun ListTransactionsScreenSingleTransactionPreview() {
    MaterialTheme {
        ListTransactionsScreenContent(
            transactions = listOf(previewTransactions.first()),
            isLoading = false
        )
    }
}

@Preview(name = "List - Many Transactions", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun ListTransactionsScreenManyTransactionsPreview() {
    val manyTransactions = previewTransactions + previewTransactions.mapIndexed { index, transaction ->
        transaction.copy(
            id = "extra_${index}",
            date = "0${index + 6}-12-2023",
            amount = transaction.amount * (index + 1)
        )
    }
    
    MaterialTheme {
        ListTransactionsScreenContent(
            transactions = manyTransactions,
            isLoading = false
        )
    }
}

// Individual component previews for development
@Preview(name = "Transaction Summary - Rich Data", showBackground = true)
@Composable
fun TransactionSummaryRichPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            TransactionSummary(transactions = previewTransactions)
        }
    }
}

@Preview(name = "Transaction Summary - No Income", showBackground = true)
@Composable
fun TransactionSummaryNoIncomePreview() {
    val expenseOnlyTransactions = previewTransactions.filter { it.type == TransactionType.EXPENSE }
    
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            TransactionSummary(transactions = expenseOnlyTransactions)
        }
    }
}

@Preview(name = "Transaction Items - Mixed Types", showBackground = true)
@Composable
fun TransactionItemsMixedPreview() {
    MaterialTheme {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(previewTransactions.take(3)) { transaction ->
                TransactionItem(
                    transaction = transaction,
                    onClick = { }
                )
            }
        }
    }
} 