package msi.paria.domain.usecase

import msi.paria.domain.model.ExchangeRate
import msi.paria.domain.repository.ExchangeRateRepository

class GetExchangeRateUseCase(private val repository: ExchangeRateRepository) {

    suspend fun getExchangeRates(): List<ExchangeRate> {
        return try {
            return repository.getExchangeRates()
           // Resource.Success(rates)
        } catch (e: Exception) {
           // Resource.Error(e.message.toString())
            throw e
        }
    }
}