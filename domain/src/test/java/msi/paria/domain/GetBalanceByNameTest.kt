package msi.paria.domain

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import msi.paria.domain.model.Balance
import msi.paria.domain.repository.TransactionRepository
import msi.paria.domain.usecase.GetBalanceByName
import org.junit.Assert.assertEquals
import org.junit.Test

class GetBalanceByNameTest {

    private val mockTransactionRepository: TransactionRepository = mockk()
    private val getBalanceByName = GetBalanceByName(mockTransactionRepository)

    @Test
    fun `when invoke with valid name then return balance`() {
        // Given
        val name = "John"
        val expectedBalance = mockk<Balance>(relaxed = true)

        coEvery { mockTransactionRepository.getBalanceByName(name) } returns expectedBalance

        // When
        val result = runBlocking { getBalanceByName(name) }

        // Then
        assertEquals(expectedBalance, result)
    }

    @Test
    fun `when invoke with invalid name then return null`() {
        // Given
        val name = "NonExistingName"

        coEvery { mockTransactionRepository.getBalanceByName(name) } returns null

        // When
        val result = runBlocking { getBalanceByName(name) }

        // Then
        assertEquals(null, result)
    }
}