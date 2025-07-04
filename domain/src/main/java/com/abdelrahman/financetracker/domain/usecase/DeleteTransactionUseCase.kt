package com.abdelrahman.financetracker.domain.usecase

import com.abdelrahman.financetracker.domain.model.Transaction
import com.abdelrahman.financetracker.domain.repository.TransactionRepository
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction) {
        repository.deleteTransaction(transaction)
    }
} 