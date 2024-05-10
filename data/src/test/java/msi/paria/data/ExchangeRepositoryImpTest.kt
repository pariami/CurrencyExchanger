package msi.paria.data

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import msi.paria.data.dataprovider.CurrencyDataProvider
import msi.paria.data.repository.ExchangeRepositoryImp
import msi.paria.domain.model.Currency
import msi.paria.domain.model.Resource
import org.junit.Assert.assertEquals
import org.junit.Test

class ExchangeRepositoryImpTest {

    @Test
    fun givenValidBaseCurrency_WhenGetExchangeRatesIsCalled_ThenReturnFlowOfCurrencyResource() =
        runBlocking {
            // Given
            val currency = mockk<Currency>(relaxed = true)
            val dataProvider = mockk<CurrencyDataProvider>()
            val baseCurrency = "USD"
            val exchangeRateFlow = flowOf(Resource.Success(currency))
            coEvery { dataProvider.getExchangeRate(baseCurrency) } returns exchangeRateFlow
            val repository = ExchangeRepositoryImp(dataProvider)

            // When
            val result = repository.getExchangeRates(baseCurrency)

            // Then
            assertEquals(exchangeRateFlow, result)
            coVerify { dataProvider.getExchangeRate(baseCurrency) }
        }
}
