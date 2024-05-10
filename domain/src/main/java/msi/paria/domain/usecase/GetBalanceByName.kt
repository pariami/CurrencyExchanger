package msi.paria.domain.usecase

import msi.paria.domain.model.Balance
import msi.paria.domain.repository.TransactionRepository
import javax.inject.Inject

class GetBalanceByName @Inject constructor(private val transactionRepository: TransactionRepository) {
    suspend operator fun invoke(name: String): Balance? {
        return transactionRepository.getBalanceByName(name)
    }
}