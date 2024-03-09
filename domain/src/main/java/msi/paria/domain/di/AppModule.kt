package msi.paria.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import msi.paria.domain.repository.ExchangeRepository
import msi.paria.domain.repository.TransactionRepository
import msi.paria.domain.usecase.GetAllBalance
import msi.paria.domain.usecase.GetBalanceByName
import msi.paria.domain.usecase.GetExchangeRates
import msi.paria.domain.usecase.GetTransactions
import msi.paria.domain.usecase.InsertAllBalance
import msi.paria.domain.usecase.InsertBalance
import msi.paria.domain.usecase.InsertTransaction
import msi.paria.domain.usecase.UseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGetExchangeRatesUseCase(repository: ExchangeRepository): GetExchangeRates {
        return GetExchangeRates(repository)
    }

    @Singleton
    @Provides
    fun provideInsertTransactionUseCase(repository: TransactionRepository): InsertTransaction {
        return InsertTransaction(repository)
    }

    @Singleton
    @Provides
    fun provideGetTransactionsUseCase(repository: TransactionRepository): GetTransactions {
        return GetTransactions(repository)
    }

    @Singleton
    @Provides
    fun provideGetAllBalanceUseCase(repository: TransactionRepository): GetAllBalance {
        return GetAllBalance(repository)
    }

    @Singleton
    @Provides
    fun provideInsertAllBalanceUseCase(repository: TransactionRepository): InsertAllBalance {
        return InsertAllBalance(repository)
    }

    @Singleton
    @Provides
    fun provideInsertBalanceUseCase(repository: TransactionRepository): InsertBalance {
        return InsertBalance(repository)
    }
    @Singleton
    @Provides
    fun provideGetBalanceByIdUseCase(repository: TransactionRepository): GetBalanceByName {
        return GetBalanceByName(repository)
    }
    
    fun provideUseCase(
        exchangeRepository: ExchangeRepository,
        transactionRepository: TransactionRepository
    ): UseCase {
        return UseCase(
            getExchangeRates = GetExchangeRates(exchangeRepository),
            insertTransaction = InsertTransaction(transactionRepository),
            getTransactions = GetTransactions(transactionRepository),
            getAllBalance = GetAllBalance(transactionRepository),
            insertAllBalance = InsertAllBalance(transactionRepository),
            insertBalance = InsertBalance(transactionRepository),
            getBalanceByName = GetBalanceByName(transactionRepository)
        )
    }
}