package msi.paria.data.dto.api

import msi.paria.domain.model.Currency

data class CurrencyResponse(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
    //val rates: Rates
)
fun CurrencyResponse.toCurrency(): Currency{
    return Currency(base = base,
        date = date,
        rates = rates)
}