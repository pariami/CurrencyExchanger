package msi.paria.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import msi.paria.data.ServiceRepository
import msi.paria.data.repository.ServiceRepositoryImpl
import msi.paria.data.repository.api.ApiService
import msi.paria.data.repository.db.TransactionDB
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceRepoDIModule {

    @Provides
    @Singleton
    fun provideServiceRepository(apiService: ApiService, transactionDB: TransactionDB): ServiceRepository {
        return ServiceRepositoryImpl(apiService, transactionDB)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://developers.paysera.com/tasks/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()

    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTransactionDao(@ApplicationContext ctx: Context): TransactionDB {
        return TransactionDB.getDatabase(ctx)
    }

}