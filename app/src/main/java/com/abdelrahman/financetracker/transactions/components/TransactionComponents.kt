package com.abdelrahman.financetracker.transactions.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdelrahman.financetracker.domain.model.Transaction
import com.abdelrahman.financetracker.domain.model.TransactionType

@Composable
fun TransactionTypeSelector(
    selectedType: TransactionType,
    onTypeSelected: (TransactionType) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Transaction Type",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TransactionType.entries.forEach { type ->
                    val isSelected = selectedType == type
                    val (color, backgroundColor) = when (type) {
                        TransactionType.INCOME -> Color(0xFF10B981) to Color(0xFFF0FDF4)
                        TransactionType.EXPENSE -> Color(0xFFEF4444) to Color(0xFFFEF2F2)
                    }

                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onTypeSelected(type) }
                            .shadow(if (isSelected) 4.dp else 1.dp, RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) backgroundColor else Color(0xFFF8F9FA)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { onTypeSelected(type) },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = color,
                                    unselectedColor = Color(0xFF9CA3AF)
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = type.name.lowercase().replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                                color = if (isSelected) color else Color(0xFF6B7280)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategorySelector(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    categories: List<String>,
    showDropdown: Boolean,
    onDropdownToggle: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Category",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Box(
                modifier = Modifier.clickable { onDropdownToggle(!showDropdown) },
            ) {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = { },
                    readOnly = true,
                    placeholder = {
                        Text(
                            "Select category",
                            color = Color(0xFF9CA3AF)
                        )
                    },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.clickable { onDropdownToggle(!showDropdown) },
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color(0xFF6B7280)
                        )
                    },
                    modifier = Modifier
                        .clickable { onDropdownToggle(!showDropdown) }
                        .fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF007AFF),
                        unfocusedBorderColor = Color(0xFFE5E7EB),
                        focusedTextColor = Color(0xFF1A1A1A),
                        unfocusedTextColor = Color(0xFF1A1A1A)
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                DropdownMenu(
                    expanded = showDropdown,
                    onDismissRequest = { onDropdownToggle(false) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    category,
                                    color = Color(0xFF1A1A1A),
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            onClick = {
                                onCategorySelected(category)
                                onDropdownToggle(false)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AmountInput(
    amount: String,
    onAmountChanged: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Amount",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = amount,
                onValueChange = onAmountChanged,
                placeholder = {
                    Text(
                        "0.00",
                        color = Color(0xFF9CA3AF)
                    )
                },
                leadingIcon = {
                    Text(
                        "$",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF6B7280),
                        fontWeight = FontWeight.SemiBold
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF007AFF),
                    unfocusedBorderColor = Color(0xFFE5E7EB),
                    focusedTextColor = Color(0xFF1A1A1A),
                    unfocusedTextColor = Color(0xFF1A1A1A)
                ),
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}

@Composable
fun DateInput(
    date: String,
    onDateChanged: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Date",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = date,
                onValueChange = onDateChanged,
                placeholder = {
                    Text(
                        "DD-MM-YYYY",
                        color = Color(0xFF9CA3AF)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF007AFF),
                    unfocusedBorderColor = Color(0xFFE5E7EB),
                    focusedTextColor = Color(0xFF1A1A1A),
                    unfocusedTextColor = Color(0xFF1A1A1A)
                ),
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}

@Composable
fun NotesInput(
    notes: String,
    onNotesChanged: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Notes (Optional)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = notes,
                onValueChange = onNotesChanged,
                placeholder = {
                    Text(
                        "Add a note about this transaction...",
                        color = Color(0xFF9CA3AF)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF007AFF),
                    unfocusedBorderColor = Color(0xFFE5E7EB),
                    focusedTextColor = Color(0xFF1A1A1A),
                    unfocusedTextColor = Color(0xFF1A1A1A)
                ),
                shape = RoundedCornerShape(12.dp),
                maxLines = 4
            )
        }
    }
}

@Composable
fun SaveButton(
    enabled: Boolean,
    onSave: () -> Unit
) {
    Button(
        onClick = onSave,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(if (enabled) 8.dp else 2.dp, RoundedCornerShape(16.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF007AFF),
            disabledContainerColor = Color(0xFFE5E7EB)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = if (enabled) Color.White else Color(0xFF9CA3AF),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Save Transaction",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = if (enabled) Color.White else Color(0xFF9CA3AF)
            )
        }
    }
}

@Composable
fun TransactionItem(
    transaction: Transaction,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Modern Icon Design
            val (icon, iconColor, bgColor) = when (transaction.type) {
                TransactionType.INCOME -> Triple(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    Color.White,
                    Color(0xD910B981)
                )

                TransactionType.EXPENSE -> Triple(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    Color.White,
                    Color(0xD9EF4444)
                )
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(bgColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = transaction.type.name,
                    tint = iconColor,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Transaction Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = transaction.category,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1A1A1A),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = transaction.date,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF6B7280),
                        fontWeight = FontWeight.Medium
                    )
                    if (!transaction.notes.isNullOrBlank()) {
                        Text(
                            text = " • ${transaction.notes}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF9CA3AF),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            // Amount with modern styling
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${if (transaction.type == TransactionType.EXPENSE) "-" else "+"}$%.2f".format(
                        transaction.amount
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = when (transaction.type) {
                        TransactionType.INCOME -> Color(0xFF10B981)
                        TransactionType.EXPENSE -> Color(0xFF1A1A1A)
                    },
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun TransactionSummary(transactions: List<Transaction>,onClick: () -> Unit = {}) {
    val totalIncome = transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
    val totalExpense = transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
    val balance = totalIncome - totalExpense

    Column(
        modifier = Modifier.clickable { onClick() },
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Main Balance Card
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFF1A1A1A)
        ) {
            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Total Balance",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$%.2f".format(balance),
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = (if (balance >= 0) "+2.5% from last month" else "-1.2% from last month").plus(" dummy info"),
                    style = MaterialTheme.typography.bodySmall,
                    color = if (balance >= 0) Color(0xFF34D399) else Color(0xFFF87171),
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Income & Expense Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ModernSummaryCard(
                title = "Income",
                amount = totalIncome,
                icon = Icons.Default.Add,
                color = Color(0xFF10B981),
                backgroundColor = Color(0xFFF0FDF4),
                modifier = Modifier.weight(1f)
            )
            ModernSummaryCard(
                title = "Expenses",
                amount = totalExpense,
                icon = Icons.Default.Add,
                color = Color(0xFFEF4444),
                backgroundColor = Color(0xFFFEF2F2),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ModernSummaryCard(
    title: String,
    amount: Double,
    icon: ImageVector,
    color: Color,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .shadow(4.dp, RoundedCornerShape(20.dp))
            .clickable { },
        shape = RoundedCornerShape(20.dp),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6B7280),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$%.0f".format(amount),
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF1A1A1A),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Preview Methods
@Preview(showBackground = true)
@Composable
fun TransactionTypeSelectorPreview() {
    var selectedType by remember { mutableStateOf(TransactionType.EXPENSE) }
    
    TransactionTypeSelector(
        selectedType = selectedType,
        onTypeSelected = { selectedType = it }
    )
}

@Preview(showBackground = true)
@Composable
fun CategorySelectorPreview() {
    var selectedCategory by remember { mutableStateOf("") }
    var showDropdown by remember { mutableStateOf(false) }
    
    CategorySelector(
        selectedCategory = selectedCategory,
        onCategorySelected = { selectedCategory = it },
        categories = listOf("Groceries", "Transportation", "Entertainment", "Bills"),
        showDropdown = showDropdown,
        onDropdownToggle = { showDropdown = it }
    )
}

@Preview(showBackground = true)
@Composable
fun AmountInputPreview() {
    var amount by remember { mutableStateOf("") }
    
    AmountInput(
        amount = amount,
        onAmountChanged = { amount = it }
    )
}

@Preview(showBackground = true)
@Composable
fun DateInputPreview() {
    var date by remember { mutableStateOf("") }
    
    DateInput(
        date = date,
        onDateChanged = { date = it }
    )
}

@Preview(showBackground = true)
@Composable
fun NotesInputPreview() {
    var notes by remember { mutableStateOf("") }
    
    NotesInput(
        notes = notes,
        onNotesChanged = { notes = it }
    )
}

@Preview(showBackground = true)
@Composable
fun SaveButtonPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        SaveButton(
            enabled = true,
            onSave = { }
        )
        SaveButton(
            enabled = false,
            onSave = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionItemPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        TransactionItem(
            transaction = Transaction(
                id = "1",
                type = TransactionType.INCOME,
                category = "Salary",
                amount = 5000.0,
                date = "16-12-2023",
                notes = "Monthly salary payment"
            )
        )
        
        TransactionItem(
            transaction = Transaction(
                id = "2",
                type = TransactionType.EXPENSE,
                category = "Groceries",
                amount = 125.50,
                date = "15-12-2023",
                notes = "Weekly grocery shopping"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionSummaryPreview() {
    val sampleTransactions = listOf(
        Transaction(
            id = "1",
            type = TransactionType.INCOME,
            category = "Salary",
            amount = 5000.0,
            date = "16-12-2023",
            notes = "Monthly salary"
        ),
        Transaction(
            id = "2",
            type = TransactionType.EXPENSE,
            category = "Groceries",
            amount = 125.50,
            date = "15-12-2023",
            notes = "Weekly groceries"
        ),
        Transaction(
            id = "3",
            type = TransactionType.EXPENSE,
            category = "Transportation",
            amount = 45.0,
            date = "14-12-2023",
            notes = "Uber ride"
        )
    )
    
    TransactionSummary(transactions = sampleTransactions)
}

@Preview(showBackground = true)
@Composable
fun ModernSummaryCardPreview() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        ModernSummaryCard(
            title = "Income",
            amount = 5000.0,
            icon = Icons.Default.Add,
            color = Color(0xFF10B981),
            backgroundColor = Color(0xFFF0FDF4),
            modifier = Modifier.weight(1f)
        )
        ModernSummaryCard(
            title = "Expenses",
            amount = 1250.0,
            icon = Icons.Default.Add,
            color = Color(0xFFEF4444),
            backgroundColor = Color(0xFFFEF2F2),
            modifier = Modifier.weight(1f)
        )
    }
} 