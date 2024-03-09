package msi.paria.data.dataprovider

import kotlinx.coroutines.flow.Flow
import msi.paria.data.ServiceRepository
import msi.paria.data.dataprovider.CurrencyDataProvider
import msi.paria.data.dto.api.CurrencyResponse
import msi.paria.data.dto.db.TransactionDto
import msi.paria.domain.model.Balance
import msi.paria.domain.model.Currency
import msi.paria.domain.model.Resource
import msi.paria.domain.model.Transaction
import javax.inject.Inject


internal class CurrencyDataProviderImpl @Inject constructor(private val repository: ServiceRepository) :
    CurrencyDataProvider {
    override suspend fun getExchangeRate(base: String): Flow<Resource<Currency>> {
       return repository.getExchangeRate(base)
    }

    override suspend fun getTransactions(): List<Transaction> {
        return repository.getTransactions()
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        return repository.insertTransaction(transaction)
    }

    override suspend fun getBalanceById(name:String): Balance? {
        return repository.getBalanceById(name)
    }

    override suspend fun insertBalance(balance: Balance) {
        return repository.insertBalance(balance)
    }

    override suspend fun insertAllBalance(balances: List<Balance>) {
        return repository.insertAllBalance(balances)
    }

    override suspend fun getAllBalance(): Flow<List<Balance>> {
        return repository.getAllBalance()
    }

}