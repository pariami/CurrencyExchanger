package msi.paria.domain.usecase

import javax.inject.Inject

class CheckBalance @Inject constructor(private val getBalanceByNameUsecase: GetBalanceByName) {

    suspend operator fun invoke(currency: String, amount: Double): Boolean {
        val balance = getBalanceByNameUsecase(currency)
        return (balance?.amount ?: 0.0) >= amount
    }
}