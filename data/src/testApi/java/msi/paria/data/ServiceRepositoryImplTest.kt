package msi.paria.data

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import msi.paria.data.dto.api.CurrencyResponse
import msi.paria.data.dto.api.toCurrency
import msi.paria.data.repository.ServiceRepositoryImpl
import msi.paria.data.repository.api.ApiService
import msi.paria.data.repository.db.TransactionDB
import msi.paria.domain.model.Currency
import msi.paria.domain.model.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class ServiceRepositoryImplTest {

    @Test
    fun `given valid base currency, when getExchangeRate is called, then return success resource`() =
        runBlocking {
            // Given
            val apiService = mockk<ApiService>()
            val transactionDB = mockk<TransactionDB>()
            val repository = ServiceRepositoryImpl(apiService, transactionDB)
            val baseCurrency = "USD"
            val exchangeRateResponse = mockk<CurrencyResponse>(relaxed = true)
            coEvery { apiService.getExchangeRates(baseCurrency) } returns retrofit2.Response.success(
                exchangeRateResponse
            )

            // When
            val result = repository.getExchangeRate(baseCurrency)

            // Then
            result.collect { resource ->
                assertNotNull(resource as? Resource.Success<Currency>)
                assertEquals(
                    exchangeRateResponse.toCurrency(),
                    (resource as Resource.Success<Currency>).data
                )
            }
            coVerify { apiService.getExchangeRates(baseCurrency) }
        }
}
