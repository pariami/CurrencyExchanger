package msi.paria.domain.usecase

import msi.paria.domain.model.Balance
import msi.paria.domain.repository.TransactionRepository
import javax.inject.Inject

class InsertAllBalance @Inject constructor(val transactionRepository: TransactionRepository) {
    suspend operator fun invoke(balances: List<Balance>) {
        transactionRepository.insertAllBalance(balances)
    }
}