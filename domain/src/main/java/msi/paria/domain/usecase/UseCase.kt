package msi.paria.domain.usecase

import javax.inject.Inject

data class UseCase @Inject constructor(
    val getExchangeRates: GetExchangeRates,
    val insertTransaction: InsertTransaction,
    val getTransactions: GetTransactions,
    val getAllBalance: GetAllBalance,
    val insertBalance: InsertBalance,
    val insertAllBalance: InsertAllBalance,
    val getBalanceByName: GetBalanceByName
)