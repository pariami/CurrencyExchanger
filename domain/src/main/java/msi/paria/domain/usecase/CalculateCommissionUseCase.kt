package msi.paria.domain.usecase

class CalculateCommissionUseCase {
    fun invoke(totalTransactions: Int): Double {
        return if (totalTransactions <= 5) {
            0.0
        } else {
            // Assuming 0.7% commission fee
            totalTransactions * 0.007
        }
    }
}