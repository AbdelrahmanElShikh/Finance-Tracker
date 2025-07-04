package com.abdelrahman.financetracker.domain.usecase

import com.abdelrahman.financetracker.domain.model.Transaction
import com.abdelrahman.financetracker.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTransactionByIdUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(id: String): Transaction? {
        return repository.getTransactionById(id)
    }
} 