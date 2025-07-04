package com.abdelrahman.financetracker.transactions.mockTransactions

import com.abdelrahman.financetracker.domain.model.Transaction
import com.abdelrahman.financetracker.domain.model.TransactionType


// Fake data for demonstration
fun getFakeTransactions(): List<Transaction> {
    return listOf(
        Transaction(
            id = "1",
            type = TransactionType.INCOME,
            category = "Salary",
            amount = 5000.00,
            date = "16-9-23",
            notes = "Monthly salary payment"
        ),
        Transaction(
            id = "2",
            type = TransactionType.EXPENSE,
            category = "Groceries",
            amount = 125.50,
            date = "16-9-23",
            notes = "Weekly grocery shopping at Walmart"
        ),
        Transaction(
            id = "3",
            type = TransactionType.EXPENSE,
            category = "Bills",
            amount = 89.99,
            date = "16-9-23",
            notes = "Internet bill payment"
        ),
        Transaction(
            id = "4",
            type = TransactionType.INCOME,
            category = "Freelance",
            amount = 750.00,
            date = "16-9-23",
            notes = "Web design project payment"
        ),
        Transaction(
            id = "5",
            type = TransactionType.EXPENSE,
            category = "Entertainment",
            amount = 45.00,
            date = "16-9-23",
            notes = "Movie tickets and popcorn"
        ),
        Transaction(
            id = "6",
            type = TransactionType.EXPENSE,
            category = "Transportation",
            amount = 25.75,
            date = "16-9-23",
            notes = "Uber ride to downtown"
        ),
        Transaction(
            id = "7",
            type = TransactionType.INCOME,
            category = "Investment",
            amount = 200.00,
            date = "16-9-23",
            notes = "Dividend payment from stocks"
        ),
        Transaction(
            id = "8",
            type = TransactionType.EXPENSE,
            category = "Food & Dining",
            amount = 67.80,
            date = "16-9-23",
            notes = "Dinner at Italian restaurant"
        ),
        Transaction(
            id = "9",
            type = TransactionType.EXPENSE,
            category = "Health",
            amount = 150.00,
            date = "16-9-23",
            notes = "Doctor consultation fee"
        ),
        Transaction(
            id = "10",
            type = TransactionType.EXPENSE,
            category = "Travel",
            amount = 320.00,
            date = "16-9-23",
            notes = "Flight booking for weekend trip"
        ),
        Transaction(
            id = "11",
            type = TransactionType.INCOME,
            category = "Side Business",
            amount = 450.00,
            date = "16-9-23",
            notes = "Online course sales"
        ),
        Transaction(
            id = "12",
            type = TransactionType.EXPENSE,
            category = "Shopping",
            amount = 89.99,
            date = "16-9-23",
            notes = "New workout clothes"
        )
    )
}

// Get categories based on selected type
val incomeCategories = listOf(
    "Salary", "Freelance", "Investment", "Side Business", "Rental Income",
    "Dividends", "Interest", "Bonus", "Commission", "Gifts Received",
    "Tax Refund", "Business Income", "Other Income"
)

val expenseCategories = listOf(
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