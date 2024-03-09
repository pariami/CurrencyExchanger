package msi.paria.domain.model

class Transaction(
    val id: Int = 0,
    val amount: Double,
    val fromCurrency: String,
    val toCurrency: String,
    val convertedAmount: Double,
    val commissionFee: Double
)