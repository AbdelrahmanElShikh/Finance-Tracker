package com.abdelrahman.financetracker.domain.usecase

import com.abdelrahman.financetracker.domain.model.Transaction
import com.abdelrahman.financetracker.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTransactionsByCategoryUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(category: String): List<Transaction> {
        return repository.getTransactionsByCategory(category)
    }
} 