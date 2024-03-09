package msi.paria.domain.repository

import kotlinx.coroutines.flow.Flow
import msi.paria.domain.model.Currency
import msi.paria.domain.model.Resource

interface ExchangeRepository {
    suspend fun getExchangeRates(base:String): Flow<Resource<Currency>>
}