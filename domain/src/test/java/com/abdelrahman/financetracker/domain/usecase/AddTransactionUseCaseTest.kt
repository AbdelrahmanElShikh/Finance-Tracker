package com.abdelrahman.financetracker.domain.usecase

import com.abdelrahman.financetracker.domain.model.Transaction
import com.abdelrahman.financetracker.domain.model.TransactionType
import com.abdelrahman.financetracker.domain.repository.TransactionRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class AddTransactionUseCaseTest {

    @Mock
    private lateinit var repository: TransactionRepository

    private lateinit var addTransactionUseCase: AddTransactionUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        addTransactionUseCase = AddTransactionUseCase(repository)
    }

    @Test
    fun `invoke should call repository insertTransaction`() = runTest {
        // Given
        val transaction = Transaction(
            id = "1",
            type = TransactionType.EXPENSE,
            category = "Food",
            amount = 50.0,
            date = "2024-01-01",
            notes = "Test transaction"
        )

        // When
        addTransactionUseCase(transaction)

        // Then
        verify(repository).insertTransaction(transaction)
    }
} 