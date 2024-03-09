package msi.paria.domain.usecase

import msi.paria.domain.model.Balance
import msi.paria.domain.model.Transaction
import msi.paria.domain.repository.TransactionRepository

class InsertAllBalance(val transactionRepository: TransactionRepository){
    suspend operator fun invoke(balances: List<Balance>){
        transactionRepository.insertAllBalance(balances)
    }
}