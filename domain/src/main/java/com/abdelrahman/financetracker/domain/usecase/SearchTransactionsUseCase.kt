package com.abdelrahman.financetracker.domain.usecase

import com.abdelrahman.financetracker.domain.model.Transaction
import com.abdelrahman.financetracker.domain.repository.TransactionRepository
import javax.inject.Inject

class SearchTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(searchQuery: String): List<Transaction> {
        return repository.searchTransactions(searchQuery)
    }
} 