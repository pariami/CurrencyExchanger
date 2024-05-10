package msi.paria.domain

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import msi.paria.domain.model.Transaction
import msi.paria.domain.usecase.GetCommission
import msi.paria.domain.usecase.GetTransactions
import org.junit.Assert.assertEquals
import org.junit.Test

class GetCommissionTest {

    private val mockGetTransactionsUseCase: GetTransactions = mockk()
    private val getCommission = GetCommission(mockGetTransactionsUseCase)

    @Test
    fun `when invoke and transactions less than 5 then return 0 commission`() {
        // Given
        val transactions = listOf<Transaction>() // Assuming an empty list for simplicity
        val expectedCommission = 0.0

        coEvery { mockGetTransactionsUseCase() } returns transactions

        // When
        val result = runBlocking { getCommission() }

        // Then
        assertEquals(expectedCommission, result, 0.0)
    }

    @Test
    fun `when invoke and transactions equal to or more than 5 then return commission`() {
        // Given
        val mockkTransaction = mockk<Transaction>(relaxed = true)
        val transactions = listOf(
            mockkTransaction,
            mockkTransaction,
            mockkTransaction,
            mockkTransaction,
            mockkTransaction
        ) // Assuming 5 transactions for simplicity
        val expectedCommission = 0.7

        coEvery { mockGetTransactionsUseCase() } returns transactions

        // When
        val result = runBlocking { getCommission() }

        // Then
        assertEquals(expectedCommission, result, 0.0)
    }
}