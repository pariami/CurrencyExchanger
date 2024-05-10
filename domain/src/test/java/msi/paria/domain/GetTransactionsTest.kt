package msi.paria.domain

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import msi.paria.domain.model.Transaction
import msi.paria.domain.repository.TransactionRepository
import msi.paria.domain.usecase.GetTransactions
import org.junit.Assert.assertEquals
import org.junit.Test

class GetTransactionsTest {

    private val mockTransactionRepository: TransactionRepository = mockk()
    private val getTransactions = GetTransactions(mockTransactionRepository)

    @Test
    fun `when invoke then return list of transactions`() {
        // Given
        val expectedTransactions = mockk<List<Transaction>>(relaxed = true)

        coEvery { mockTransactionRepository.getTransactions() } returns expectedTransactions

        // When
        val result = runBlocking { getTransactions() }

        // Then
        assertEquals(expectedTransactions, result)
    }
}
