package com.paypay.currency_converter.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class CurrencyListModel (
    @Json(name="success")
     val status:Boolean,
     @Json(name="currencies")
     val currencyMap:Map<String,String>,
    @Json(name="error")
    val errorInfo:ApiErrorInfo?
)
{

     val currencyDescpList:List<CurrencyDescription>
    get() {
        val currencyList= mutableListOf<CurrencyDescription>()
        if(status && !currencyMap.isNullOrEmpty())
        {
            currencyMap.map {
             val currencyDescp=CurrencyDescription(it.key,it.value)
                currencyList.add(currencyDescp)
            }
        }
        return currencyList
    }
    val currencyCode:String
    get() {
        var currencyCodesString=""
        if(this.status && !currencyMap.isNullOrEmpty())
            currencyMap.keys.joinToString().also { currencyCodesString = it }
        return currencyCodesString
    }
}