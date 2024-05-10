package msi.paria.data.repository

import kotlinx.coroutines.flow.Flow
import msi.paria.data.dataprovider.CurrencyDataProvider
import msi.paria.domain.model.Balance
import msi.paria.domain.model.Transaction
import msi.paria.domain.repository.TransactionRepository

class TransactionRepositoryImp(
    val dataProvider: CurrencyDataProvider
) : TransactionRepository {
    override suspend fun getTransactions(): List<Transaction> {
        return dataProvider.getTransactions()
    }

    override suspend fun insertTransaction(transaction: Transaction) {
         dataProvider.insertTransaction(transaction)

    }

    override suspend fun getBalanceByName(name: String): Balance? {
        return dataProvider.getBalanceById(name)
    }

    override suspend fun getAllBalance(): Flow<List<Balance>> {
       return dataProvider.getAllBalance()
    }

    override suspend fun insertBalance(balance: Balance) {
         dataProvider.insertBalance(balance)
    }

    override suspend fun insertAllBalance(balances: List<Balance>) {
        dataProvider.insertAllBalance(balances)
    }
}