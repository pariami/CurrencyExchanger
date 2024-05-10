package msi.paria.domain

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import msi.paria.domain.model.Balance
import msi.paria.domain.repository.TransactionRepository
import msi.paria.domain.usecase.GetAllBalance
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class GetAllBalanceTest {

    private val mockTransactionRepository: TransactionRepository = mockk()
    private val getAllBalance = GetAllBalance(mockTransactionRepository)

    @Test
    fun `Given transaction repository returns balances, When getAllBalance is invoked, Then return flow of balances`() =
        runBlocking {
            // Given
            val expectedBalances = listOf(
                Balance(id = 1, amount = 100.0, name = "USD"),
                Balance(id = 2, amount = 200.0, name = "EUR")
            )
            coEvery { mockTransactionRepository.getAllBalance() } returns flowOf(expectedBalances)

            // When
            val actualFlow = getAllBalance()

            // Then
            actualFlow.collect { actualBalances ->
                assertEquals(expectedBalances.size, actualBalances.size)
                assertEquals(expectedBalances, actualBalances)
            }
        }
}