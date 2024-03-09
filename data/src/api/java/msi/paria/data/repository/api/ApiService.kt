package msi.paria.data.repository.api


import msi.paria.data.dto.api.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("currency-exchange-rates")
    suspend fun getExchangeRates(@Query("base") base:String): Response<CurrencyResponse>
}