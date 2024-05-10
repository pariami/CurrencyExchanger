package msi.paria.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import msi.paria.data.ServiceRepository
import msi.paria.data.dataprovider.CurrencyDataProvider
import msi.paria.data.dataprovider.CurrencyDataProviderImpl
import msi.paria.data.repository.ExchangeRepositoryImp
import msi.paria.data.repository.TransactionRepositoryImp
import msi.paria.domain.repository.ExchangeRepository
import msi.paria.domain.repository.TransactionRepository

@Module
@InstallIn(SingletonComponent::class)
internal class CurrencyDIModule {

    @Provides
    fun provideItemDataProvider(repo: ServiceRepository): CurrencyDataProvider {
        return CurrencyDataProviderImpl(repo)
    }

    @Provides
    fun provideItemRepository(dataProvider: CurrencyDataProvider): TransactionRepository {
        return TransactionRepositoryImp(dataProvider)
    }

    @Provides
    fun provideExchangeRepository(dataProvider: CurrencyDataProvider): ExchangeRepository {
        return ExchangeRepositoryImp(dataProvider)
    }
}