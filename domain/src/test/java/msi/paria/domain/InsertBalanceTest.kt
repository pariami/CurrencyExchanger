package msi.paria.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import msi.paria.domain.model.Balance
import msi.paria.domain.repository.TransactionRepository
import msi.paria.domain.usecase.InsertBalance
import org.junit.Test

class InsertBalanceTest {

    private val transactionRepository = mockk<TransactionRepository>()
    private val insertBalance = InsertBalance(transactionRepository)

    @Test
    fun `given a balance when invoke then insert the balance`() {
        // Given
        val balance = mockk<Balance>(relaxed = true)

        coEvery { transactionRepository.insertBalance(balance) } returns Unit

        // When
        runBlocking {
            insertBalance(balance)
        }

        // Then
        coVerify { transactionRepository.insertBalance(balance) }
    }
}