package msi.paria.domain.model


data class Transaction(
    val amount: Double,
    val fromCurrency: String,
    val toCurrency: String,
  /*  val convertedAmount: Double,
    val commissionFee: Double*/
)