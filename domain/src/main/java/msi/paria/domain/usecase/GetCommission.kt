package msi.paria.domain.usecase

import javax.inject.Inject

class GetCommission @Inject constructor(private val getTransactionsUseCase: GetTransactions) {

    suspend operator fun invoke(): Double {
        val transactions = getTransactionsUseCase()
        val commissionFee = if (transactions.size < 5) 0.0 else 0.7
        return commissionFee
    }
}