package msi.paria.domain.usecase

import msi.paria.domain.model.InsufficientBalanceException
import msi.paria.domain.model.InvalidExchangeRateException
import msi.paria.domain.model.Transaction
import msi.paria.domain.repository.ExchangeRateRepository

class ConvertCurrencyUseCase(private val repository: ExchangeRateRepository) {
    private var exchangeCount: Int = 0

    suspend operator fun invoke(
        transaction: Transaction
    ): Double {
        val exchangeRates = repository.getExchangeRates()
        val exchangeRate = exchangeRates.find { it.fromCurrency == transaction.fromCurrency && it.toCurrency == transaction.toCurrency }
            ?: throw InvalidExchangeRateException("Exchange rate not found")

        val convertedAmount = transaction.amount * exchangeRate.rate
        val commissionFee = calculateCommissionFee(transaction.amount)

        val finalAmount = convertedAmount - commissionFee
        if (finalAmount < 0) {
            throw InsufficientBalanceException("Insufficient balance after conversion")
        }

        // Update balance (pseudo code)
        repository.updateBalance(transaction.fromCurrency, -transaction.amount)
        repository.updateBalance(transaction.toCurrency, finalAmount)

        return finalAmount
    }

    // Calculate commission fee
    private fun calculateCommissionFee(amount: Double): Double {
        var freeExchanges = 5
        val commissionRate = 0.007 // 0.7%

        // Pseudo code: Check the number of exchanges and apply fee accordingly
        // You'll need to keep track of the exchange count somewhere
        // For simplicity, assuming all exchanges are charged after the free ones
        return if (freeExchanges > 0) {
            freeExchanges--
            0.0
        } else {
            amount * commissionRate
        }
    }
    private fun updateExchangeCount() {
        exchangeCount++
    }
}
