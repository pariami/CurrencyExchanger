package msi.paria.domain.repository

import kotlinx.coroutines.flow.Flow
import msi.paria.domain.model.Balance
import msi.paria.domain.model.Transaction


interface TransactionRepository {
    suspend fun getTransactions(): List<Transaction>
    suspend fun insertTransaction(transaction: Transaction)

    suspend fun getBalanceByName(name:String):Balance?
    suspend fun getAllBalance(): Flow<List<Balance>>
    suspend fun insertBalance(balance: Balance)
    suspend fun insertAllBalance(balances: List<Balance>)
}