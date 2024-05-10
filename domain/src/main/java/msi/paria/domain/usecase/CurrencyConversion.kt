package msi.paria.domain.usecase

import msi.paria.domain.model.Currency
import msi.paria.domain.model.Resource
import javax.inject.Inject
import kotlin.math.round

class CurrencyConversion @Inject constructor() {
    operator fun invoke(
        ratesResponse: Resource<Currency>,
        amountStr: String,
        fromCurrency: String,
        toCurrency: String
    ): Double {
        val fromAmount = amountStr.toFloatOrNull() ?: return 0.0
        if (ratesResponse is Resource.Success) {
            val rates = ratesResponse.data!!.rates
            val fromRate: Double = rates[fromCurrency] ?: 0.0
            val toRate: Double = rates[toCurrency] ?: 0.0

            val conversionRate = toRate / fromRate
            return round(fromAmount * conversionRate * 100) / 100
        }
        return 0.0
    }
}