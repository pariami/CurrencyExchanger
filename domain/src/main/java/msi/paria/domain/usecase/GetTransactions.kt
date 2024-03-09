package msi.paria.domain.usecase

import msi.paria.domain.model.Transaction
import msi.paria.domain.repository.TransactionRepository

class GetTransactions(val transactionRepository: TransactionRepository){
    suspend operator fun invoke(): List<Transaction> {
        return transactionRepository.getTransactions()
    }
}