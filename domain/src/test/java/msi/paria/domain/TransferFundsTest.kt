package msi.paria.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import msi.paria.domain.model.Balance
import msi.paria.domain.usecase.GetBalanceByName
import msi.paria.domain.usecase.InsertBalance
import msi.paria.domain.usecase.TransferFunds
import org.junit.Test

class TransferFundsTest {

    private val getBalanceByName: GetBalanceByName = mockk(relaxed = true)
    private val insertBalance: InsertBalance = mockk(relaxed = true)
    private val transferFunds = TransferFunds(getBalanceByName, insertBalance)

    @Test
    fun `given valid currencies and amounts, when invoking transferFunds, then balances should be updated accordingly`() {
        // Given
        val fromCurrency = "USD"
        val toCurrency = "EUR"
        val fromAmount = 100.0
        val convertedCurrency = 80.0

        val fromBalance = Balance(1, fromCurrency, 100.0)
        val toBalance = Balance(2, toCurrency, 50.0)

        coEvery { getBalanceByName(fromCurrency) } returns fromBalance
        coEvery { getBalanceByName(toCurrency) } returns toBalance

        // When
        runBlocking { transferFunds(fromCurrency, toCurrency, fromAmount, convertedCurrency) }

        // Then
        coVerify {
            insertBalance(Balance(1, fromCurrency, 0.0))
            insertBalance(Balance(2, toCurrency, 130.0))
        }
    }

    @Test
    fun `given invalid fromCurrency, when invoking transferFunds, then fromBalance should be initialized with default values`() {
        // Given
        val fromCurrency = "USD"
        val toCurrency = "EUR"
        val fromAmount = 100.0
        val convertedCurrency = 80.0

        coEvery { getBalanceByName(fromCurrency) } returns null
        coEvery { getBalanceByName(toCurrency) } returns Balance(2, toCurrency, 50.0)

        // When
        runBlocking { transferFunds(fromCurrency, toCurrency, fromAmount, convertedCurrency) }

        // Then
        coVerify { insertBalance(Balance(0, fromCurrency, -100.0)) }
        coVerify { insertBalance(Balance(2, toCurrency, 130.0)) }
    }
}