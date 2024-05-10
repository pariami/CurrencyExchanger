package msi.paria.domain.usecase

import msi.paria.domain.model.Balance

class TransferFunds(
    private val getBalanceByName: GetBalanceByName,
    private val insertBalance: InsertBalance
) {
    suspend operator fun invoke(
        fromCurrency: String,
        toCurrency: String,
        fromAmount: Double,
        convertedCurrency: Double
    ) {
        val fromBalance = getBalanceByName(fromCurrency) ?: Balance(0, fromCurrency, 0.0)
        val toBalance = getBalanceByName(toCurrency) ?: Balance(0, toCurrency, 0.0)

        fromBalance.amount -= fromAmount // Subtract the transferred amount from the source balance
        toBalance.amount += convertedCurrency // Add the converted amount to the destination balance

        // Update balances
        insertBalance(fromBalance)
        insertBalance(toBalance)
    }
}