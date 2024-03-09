package msi.paria.data


import kotlinx.coroutines.flow.Flow
import msi.paria.data.dto.api.CurrencyResponse
import msi.paria.data.dto.db.TransactionDto
import msi.paria.domain.model.Balance
import msi.paria.domain.model.Currency
import msi.paria.domain.model.Resource
import msi.paria.domain.model.Transaction

interface
ServiceRepository {

    suspend fun getExchangeRate(base:String):Flow<Resource<Currency>>

    suspend fun getTransactions(): List<Transaction>

     suspend fun insertTransaction(transaction: Transaction)

    suspend fun getAllBalance(): Flow<List<Balance>>
    suspend fun insertBalance(balance: Balance)
    suspend fun insertAllBalance(balances: List<Balance>)

    suspend fun getBalanceById(name:String):Balance?

}