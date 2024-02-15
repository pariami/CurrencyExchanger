package msi.paria.domain.repository

import msi.paria.domain.model.Currency
import msi.paria.domain.model.ExchangeRate

interface ExchangeRateRepository {
    suspend fun getExchangeRates(): List<ExchangeRate>
    suspend fun getExchangeRate(
        fromCurrency: Currency,
        toCurrency: Currency
    ): Double
    suspend fun updateBalance(fromCurrency: String, amount: Double)
}