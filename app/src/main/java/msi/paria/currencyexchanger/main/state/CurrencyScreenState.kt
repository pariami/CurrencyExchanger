package msi.paria.currencyexchanger.main.state

import msi.paria.domain.model.Balance
import msi.paria.domain.model.Currency
import msi.paria.domain.model.Resource

data class CurrencyScreenState(
     val currencyResponse: Resource<Currency> = Resource.Empty(),
     val rates : Map<String, Double> = emptyMap(),
     val balances : List<Balance> = emptyList(),
     val amount: String = "",
     val fromCurrency: String = "AED",
     val toCurrency:String = "AED",
     val commissionFee: Double = 0.0,
     val convertedAmount: Double = 0.0,
     val canConvert:Boolean = false,
     val message:String = "") {
}