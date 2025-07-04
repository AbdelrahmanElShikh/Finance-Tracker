package com.abdelrahman.financetracker.currencyconverter.presentation.currencyConverter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abdelrahman.financetracker.currencyconverter.domain.model.Currency
import com.abdelrahman.financetracker.currencyconverter.presentation.currencyConverter.intnet.CurrencyConverterIntent
import com.abdelrahman.financetracker.currencyconverter.presentation.currencyConverter.state.CurrencyConverterState
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CurrencyConverterScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: CurrencyConverterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CurrencyConverterContent(
        state = state,
        onNavigateBack = onNavigateBack,
        onNavigateToSettings = onNavigateToSettings,
        onIntent = viewModel::handleIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterContent(
    state: CurrencyConverterState,
    onNavigateBack: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onIntent: (CurrencyConverterIntent) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Currency Converter",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Currency Selection Cards
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // From Currency Card
                    CurrencyCard(
                        modifier = Modifier.weight(1f),
                        currency = state.fromCurrency,
                        label = "From",
                        onClick = { onIntent(CurrencyConverterIntent.ToggleFromCurrencySelector(true)) }
                    )

                    // Swap Button
                    IconButton(
                        onClick = { onIntent(CurrencyConverterIntent.SwapCurrencies) },
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.primaryContainer,
                                CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Swap currencies",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    // To Currency Card
                    CurrencyCard(
                        modifier = Modifier.weight(1f),
                        currency = state.toCurrency,
                        label = "To",
                        onClick = { onIntent(CurrencyConverterIntent.ToggleToCurrencySelector(true)) }
                    )
                }
            }

            // Amount Input
            item {
                OutlinedTextField(
                    value = state.amount,
                    onValueChange = { onIntent(CurrencyConverterIntent.UpdateAmount(it)) },
                    label = { Text("Amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            // Convert Button
            item {
                Button(
                    onClick = { onIntent(CurrencyConverterIntent.ConvertCurrency) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = !state.isLoading && state.amount.isNotBlank(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Converting...")
                    } else {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Convert", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            // Conversion Result
            if (state.conversionResult != null) {
                item {
                    ConversionResultCard(
                        result = state.conversionResult,
                        fromCurrency = state.fromCurrency,
                        toCurrency = state.toCurrency
                    )
                }
            }

            // Error Display
            if (state.error != null) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = state.error,
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Recent Conversions
            if (state.recentConversions.isNotEmpty()) {
                item {
                    Text(
                        text = "Recent Conversions",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(state.recentConversions) { conversion ->
                    RecentConversionItem(
                        conversion = conversion,
                        currencies = state.supportedCurrencies
                    )
                }
            }
        }
    }

    // Currency Selector Bottom Sheets
    if (state.showFromCurrencySelector) {
        CurrencySelector(
            currencies = state.supportedCurrencies,
            onCurrencySelected = { currency ->
                onIntent(CurrencyConverterIntent.SelectFromCurrency(currency))
            },
            onDismiss = { onIntent(CurrencyConverterIntent.ToggleFromCurrencySelector(false)) }
        )
    }

    if (state.showToCurrencySelector) {
        CurrencySelector(
            currencies = state.supportedCurrencies,
            onCurrencySelected = { currency ->
                onIntent(CurrencyConverterIntent.SelectToCurrency(currency))
            },
            onDismiss = { onIntent(CurrencyConverterIntent.ToggleToCurrencySelector(false)) }
        )
    }
}

@Composable
fun CurrencyCard(
    modifier: Modifier = Modifier,
    currency: Currency?,
    label: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

            if (currency != null) {
                Text(
                    text = currency.flag ?: "",
                    fontSize = 24.sp
                )
                Text(
                    text = currency.code,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            } else {
                Text(
                    text = "Select",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Select currency",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun ConversionResultCard(
    result: com.abdelrahman.financetracker.currencyconverter.domain.repository.ConversionResult,
    fromCurrency: Currency?,
    toCurrency: Currency?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Conversion Result",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${formatCurrency(result.fromAmount)} ${fromCurrency?.code ?: result.fromCurrency}",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Text(
                text = "=",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Text(
                text = "${formatCurrency(result.toAmount)} ${toCurrency?.code ?: result.toCurrency}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Rate: 1 ${fromCurrency?.code ?: result.fromCurrency} = ${
                    formatCurrency(
                        result.rate
                    )
                } ${toCurrency?.code ?: result.toCurrency}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun RecentConversionItem(
    conversion: com.abdelrahman.financetracker.currencyconverter.domain.repository.ConversionResult,
    currencies: List<Currency>
) {
    val fromCurrency = currencies.find { it.code == conversion.fromCurrency }
    val toCurrency = currencies.find { it.code == conversion.toCurrency }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "${formatCurrency(conversion.fromAmount)} ${conversion.fromCurrency}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${formatCurrency(conversion.toAmount)} ${conversion.toCurrency}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = "${fromCurrency?.flag ?: ""} → ${toCurrency?.flag ?: ""}",
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun CurrencySelector(
    currencies: List<Currency>,
    onCurrencySelected: (Currency) -> Unit,
    onDismiss: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "Select Currency",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items(currencies) { currency ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCurrencySelected(currency) },
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = currency.flag ?: "",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(end = 16.dp)
                    )

                    Column {
                        Text(
                            text = currency.code,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = currency.name,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

private fun formatCurrency(amount: java.math.BigDecimal): String {
    val formatter = NumberFormat.getInstance(Locale.US)
    formatter.maximumFractionDigits = 4
    formatter.minimumFractionDigits = 2
    return formatter.format(amount)
}

@Preview(showBackground = true)
@Composable
fun CurrencyConverterScreenPreview() {
    MaterialTheme {
        CurrencyConverterContent(
            state = CurrencyConverterState(
                fromCurrency = Currency("USD", "US Dollar", "$", "🇺🇸"),
                toCurrency = Currency("EUR", "Euro", "€", "🇪🇺"),
                amount = "100",
                supportedCurrencies = listOf(
                    Currency("USD", "US Dollar", "$", "🇺🇸"),
                    Currency("EUR", "Euro", "€", "🇪🇺"),
                    Currency("GBP", "British Pound", "£", "🇬🇧")
                )
            ),
            onNavigateBack = {},
            onNavigateToSettings = {},
            onIntent = {}
        )
    }
} 