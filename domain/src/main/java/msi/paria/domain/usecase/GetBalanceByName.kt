package msi.paria.domain.usecase

import msi.paria.domain.model.Balance
import msi.paria.domain.repository.TransactionRepository

class GetBalanceByName(private val transactionRepository: TransactionRepository) {
    suspend operator fun invoke(name:String):Balance?{
       return transactionRepository.getBalanceByName(name)
    }
}