package msi.paria.domain

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import msi.paria.domain.model.Balance
import msi.paria.domain.usecase.CheckBalance
import msi.paria.domain.usecase.GetBalanceByName
import org.junit.Assert.assertEquals
import org.junit.Test

class CheckBalanceTest {

    private val mockGetBalanceByNameUsecase: GetBalanceByName = mockk()
    private val checkBalance = CheckBalance(mockGetBalanceByNameUsecase)

    @Test
    fun `Given a currency and amount, When checking balance, Then return true if balance is sufficient`() =
        runBlocking {
            // Given
            val currency = "USD"
            val amount = 100.0
            val balance =
                Balance(1, currency, amount + 50.0) // Mock balance with more than sufficient amount
            coEvery { mockGetBalanceByNameUsecase(currency) } returns balance

            // When
            val canConvert = checkBalance(currency, amount)

            // Then
            assertEquals(true, canConvert)
        }

    @Test
    fun `Given a currency and amount, When checking balance, Then return false if balance is insufficient`() =
        runBlocking {
            // Given
            val currency = "USD"
            val amount = 100.0
            val balance =
                Balance(1, currency, amount - 50.0) // Mock balance with less than sufficient amount
            coEvery { mockGetBalanceByNameUsecase(currency) } returns balance

            // When
            val canConvert = checkBalance(currency, amount)

            // Then
            assertEquals(false, canConvert)
        }

    @Test
    fun `Given a currency and amount, When checking balance with null balance, Then return false`() =
        runBlocking {
            // Given
            val currency = "USD"
            val amount = 100.0
            coEvery { mockGetBalanceByNameUsecase(currency) } returns null // Mock null balance

            // When
            val canConvert = checkBalance(currency, amount)

            // Then
            assertEquals(false, canConvert)
        }
}
