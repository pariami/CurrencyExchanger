package msi.paria.domain.usecase

import msi.paria.domain.model.Transaction
import msi.paria.domain.repository.TransactionRepository
import javax.inject.Inject

class InsertTransaction @Inject constructor(val transactionRepository: TransactionRepository) {
    suspend operator fun invoke(transaction: Transaction) {
        transactionRepository.insertTransaction(transaction)
    }
}