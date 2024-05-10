package msi.paria.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import msi.paria.domain.model.Transaction
import msi.paria.domain.repository.TransactionRepository
import msi.paria.domain.usecase.InsertTransaction
import org.junit.Test

class InsertTransactionTest {

    private val transactionRepository = mockk<TransactionRepository>()
    private val insertTransaction = InsertTransaction(transactionRepository)

    @Test
    fun `given a transaction when invoke then insert the transaction`() {
        // Given
        val transaction = mockk<Transaction>(relaxed = true)

        coEvery { transactionRepository.insertTransaction(transaction) } returns Unit

        // When
        runBlocking {
            insertTransaction(transaction)
        }

        // Then
        coVerify { transactionRepository.insertTransaction(transaction) }
    }
}