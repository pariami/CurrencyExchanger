package msi.paria.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import msi.paria.domain.model.Balance
import msi.paria.domain.repository.TransactionRepository
import msi.paria.domain.usecase.InsertAllBalance
import org.junit.Test

class InsertAllBalanceTest {

    private val transactionRepository = mockk<TransactionRepository>()
    private val insertAllBalance = InsertAllBalance(transactionRepository)

    @Test
    fun `given balances when invoke then insert all balances`() {
        // Given
        val balances = mockk<List<Balance>>(relaxed = true)

        coEvery { transactionRepository.insertAllBalance(balances) } returns Unit

        // When
        runBlocking {
            insertAllBalance(balances)
        }

        // Then
        coVerify { transactionRepository.insertAllBalance(balances) }
    }
}
