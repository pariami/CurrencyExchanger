package msi.paria.domain

import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import msi.paria.domain.model.Currency
import msi.paria.domain.model.Resource
import msi.paria.domain.usecase.CurrencyConversion
import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyConversionTest {

    private val currencyConversion = CurrencyConversion()

    @Test
    fun `Given rates response with valid rates, When currency is converted, Then return the correct converted amount`() =
        runBlocking {
            // Given
            val ratesResponse = mockk<Resource.Success<Currency>>(relaxed = true)
            val amountStr = "100"
            val fromCurrency = "USD"
            val toCurrency = "EUR"
            val ratesMap = mapOf("USD" to 1.0, "EUR" to 0.85)
            every { ratesResponse.data } returns Currency(fromCurrency, "", ratesMap)

            // When
            val convertedAmount =
                currencyConversion(ratesResponse, amountStr, fromCurrency, toCurrency)

            // Then
            assertEquals(85.0, convertedAmount, 0.001)
        }

    @Test
    fun `Given rates response with invalid rates, When currency is converted, Then return 0`() =
        runBlocking {
            // Given
            val ratesResponse: Resource<Currency> = mockk()
            val amountStr = "100"
            val fromCurrency = "USD"
            val toCurrency = "EUR"
            every { ratesResponse.data } returns null

            // When
            val convertedAmount =
                currencyConversion(ratesResponse, amountStr, fromCurrency, toCurrency)

            // Then
            assertEquals(0.0, convertedAmount, 0.001)
        }

    @Test
    fun `Given rates response with invalid amountStr, When currency is converted, Then return 0`() =
        runBlocking {
            // Given
            val ratesResponse: Resource<Currency> = mockk()
            val amountStr = "invalid_amount"
            val fromCurrency = "USD"
            val toCurrency = "EUR"
            val ratesMap = mapOf("USD" to 1.0, "EUR" to 0.85)
            every { ratesResponse.data } returns Currency(fromCurrency, "", ratesMap)

            // When
            val convertedAmount =
                currencyConversion(ratesResponse, amountStr, fromCurrency, toCurrency)

            // Then
            assertEquals(0.0, convertedAmount, 0.001)
        }
}