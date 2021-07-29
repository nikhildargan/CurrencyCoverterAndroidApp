package com.paypay.currency_converter.networking

import com.paypay.currency_converter.model.CurrencyListModel
import com.paypay.currency_converter.model.ExchangeRateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("list")
    suspend fun getCurrencyList(@Query("access_key") access_Key: String): Response<CurrencyListModel>

    @GET("live")
    suspend fun getExchangeRates(@Query("access_key") access_Key: String,@Query("currencies") currencies:String,@Query("source") source:String="",@Query("format") format:Int=1):Response<ExchangeRateResponse>
    companion object {
        const val BASE_URL = "http://api.currencylayer.com/"
    }
}