package msi.paria.domain.usecase

import msi.paria.domain.model.Balance
import msi.paria.domain.repository.TransactionRepository
import javax.inject.Inject

class InsertBalance @Inject constructor(val transactionRepository: TransactionRepository) {
    suspend operator fun invoke(balance: Balance) {
        transactionRepository.insertBalance(balance)
    }
}