package msi.paria.data.repository

import kotlinx.coroutines.flow.Flow
import msi.paria.data.dataprovider.CurrencyDataProvider
import msi.paria.domain.repository.ExchangeRepository
import msi.paria.data.dto.api.CurrencyResponse
import msi.paria.data.dto.api.toCurrency
import msi.paria.data.repository.api.ApiService
import msi.paria.domain.model.Currency
import msi.paria.domain.model.Resource
import javax.inject.Inject

class ExchangeRepositoryImp @Inject constructor(
    val dataProvider: CurrencyDataProvider
) : ExchangeRepository {
    override suspend fun getExchangeRates(base: String): Flow<Resource<Currency>> {
       return dataProvider.getExchangeRate(base)
    }
}