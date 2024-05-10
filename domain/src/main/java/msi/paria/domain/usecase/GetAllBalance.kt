package msi.paria.domain.usecase

import kotlinx.coroutines.flow.Flow
import msi.paria.domain.model.Balance
import msi.paria.domain.repository.TransactionRepository
import javax.inject.Inject

class GetAllBalance @Inject constructor(private val transactionRepository: TransactionRepository) {
    suspend operator fun invoke(): Flow<List<Balance>> {
        return transactionRepository.getAllBalance()
    }
}