package msi.paria.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import msi.paria.data.ServiceRepository
import msi.paria.data.dto.db.RateDto
import msi.paria.data.dto.db.toBalance
import msi.paria.data.dto.db.toBalanceDto
import msi.paria.data.dto.db.toCurrency
import msi.paria.data.dto.db.toTransaction
import msi.paria.data.dto.db.toTransactionDto
import msi.paria.data.repository.api.ApiService
import msi.paria.data.repository.db.TransactionDB
import msi.paria.domain.model.Balance
import msi.paria.domain.model.Currency
import msi.paria.domain.model.Resource
import msi.paria.domain.model.Transaction
import javax.inject.Inject

class ServiceRepositoryImpl @Inject constructor(
    val api: ApiService,
    val transactionDb: TransactionDB
) : ServiceRepository {

    override suspend fun getExchangeRate(base: String): Flow<Resource<Currency>> = flow {
        try {
            val response = api.getExchangeRates(base)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                transactionDb.rateDao()
                    .insert(RateDto(name = base, date = result.date, rates = result.rates))
                val currency = transactionDb.rateDao().getRateByName(base).toCurrency()
                emit(Resource.Success(currency))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getTransactions(): List<Transaction> {
        return transactionDb.transactionDao().getTransactions().map { it.toTransaction() }
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        transactionDb.transactionDao().insert(transaction.toTransactionDto())
    }

    override suspend fun getAllBalance(): Flow<List<Balance>>  = flow{
       emit(transactionDb.balanceDao().getAllBalance().map { it.toBalance() })
    }

    override suspend fun insertBalance(balance: Balance) {
        transactionDb.balanceDao().insert(balance.toBalanceDto())
    }

    override suspend fun insertAllBalance(balances: List<Balance>) {
        transactionDb.balanceDao().insertAll(balances.map { it.toBalanceDto() })
    }

    override suspend fun getBalanceById(name: String): Balance {
        return transactionDb.balanceDao().getBalanceById(name).toBalance()
    }
}