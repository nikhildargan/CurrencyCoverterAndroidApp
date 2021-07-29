package com.paypay.currency_converter.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*
@JsonClass(generateAdapter = true)
data class ExchangeRateResponse(
    @Json(name="success")
    val status:Boolean,
    @Json(name="quotes")
    val exchangeRateMap:Map<String,Double>?,
    @Json(name="error")
    val errorInfo:ApiErrorInfo?,
    @Json(name="timestamp")
    val timestamp:Long?
    ){

    val currencies: List<Currency>
        get() {
            val currencies = mutableListOf<Currency>()

            exchangeRateMap?.toSortedMap()?.map {
                val currencyCode = it.key
                val exchangeRate = exchangeRateMap[currencyCode] ?:0.0
                currencies.add(Currency(currencyCode, exchangeRate))
            }
            return currencies
        }
    //dummy list
    val dummyCurrencyList=mutableListOf(
            Currency("USDUSD",2.567),
            Currency("USDCND",2.568),
            Currency("USDMXR",2.569))

   // dummy list for CurrencyCode in case of error
    var currencyCodeList= mutableListOf("USD","CND","MXR")
}
