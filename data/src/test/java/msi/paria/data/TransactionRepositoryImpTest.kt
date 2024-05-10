package msi.paria.data

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import msi.paria.data.dataprovider.CurrencyDataProvider
import msi.paria.data.repository.TransactionRepositoryImp
import msi.paria.domain.model.Balance
import msi.paria.domain.model.Transaction
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class TransactionRepositoryImpTest {

    @Test
    fun `given valid transactions data, when getTransactions is called, then return list of transactions`() =
        runBlocking {
            // Given
            val dataProvider = mockk<CurrencyDataProvider>()
            val transactionList = listOf<Transaction>(/* Create sample transactions */)
            coEvery { dataProvider.getTransactions() } returns transactionList
            val repository = TransactionRepositoryImp(dataProvider)

            // When
            val result = repository.getTransactions()

            // Then
            assertEquals(transactionList, result)
            coVerify { dataProvider.getTransactions() }
        }

    @Test
    fun `given a transaction, when insertTransaction is called, then transaction is inserted`() =
        runBlocking {
            // Given
            val dataProvider = mockk<CurrencyDataProvider>(relaxed = true)
            val transaction = Transaction(0, 12.0, "USD", "EUR", 10.0, 7.0)
            val repository = TransactionRepositoryImp(dataProvider)

            // When
            repository.insertTransaction(transaction)

            // Then
            coVerify { dataProvider.insertTransaction(transaction) }
        }

    @Test
    fun `given a name, when getBalanceByName is called with existing name, then return balance`() =
        runBlocking {
            // Given
            val dataProvider = mockk<CurrencyDataProvider>()
            val balance = Balance(1, "EUR", 11.0)
            val name = "John"
            coEvery { dataProvider.getBalanceById(name) } returns balance
            val repository = TransactionRepositoryImp(dataProvider)

            // When
            val result = repository.getBalanceByName(name)

            // Then
            assertEquals(balance, result)
            coVerify { dataProvider.getBalanceById(name) }
        }

    @Test
    fun `given a name, when getBalanceByName is called with non-existing name, then return null`() =
        runBlocking {
            // Given
            val dataProvider = mockk<CurrencyDataProvider>()
            val name = "NonExisting"
            coEvery { dataProvider.getBalanceById(name) } returns null
            val repository = TransactionRepositoryImp(dataProvider)

            // When
            val result = repository.getBalanceByName(name)

            // Then
            assertNull(result)
            coVerify { dataProvider.getBalanceById(name) }
        }

    // Similar tests for getAllBalance, insertBalance, and insertAllBalance methods can be added.
}
