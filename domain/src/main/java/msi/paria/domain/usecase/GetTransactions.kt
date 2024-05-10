package msi.paria.domain.usecase

import msi.paria.domain.model.Transaction
import msi.paria.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTransactions @Inject constructor(val transactionRepository: TransactionRepository) {
    suspend operator fun invoke(): List<Transaction> {
        return transactionRepository.getTransactions()
    }
}