package msi.paria.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import msi.paria.domain.usecase.GetBalanceByName
import msi.paria.domain.usecase.InsertBalance
import msi.paria.domain.usecase.TransferFunds
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTransferFundsUseCase(
        getBalanceByName: GetBalanceByName, insertBalance: InsertBalance
    ): TransferFunds {
        return TransferFunds(getBalanceByName, insertBalance)
    }
}