package com.paypay.currency_converter.networking

import androidx.lifecycle.LiveData
import com.paypay.currency_converter.model.Currency
import com.paypay.currency_converter.model.ExchangeRateResponse
import com.paypay.currency_converter.model.Resource


interface Repository {

    var isFirstLaunch: Boolean
    val timestampInSeconds: Long
    fun getAllCurrencies(): LiveData<MutableList<Currency>>
    fun getSelectedCurrencies(): LiveData<MutableList<Currency>>
    fun upsertCurrency(currency: Currency)
    fun upsertCurrencies(currencies: List<Currency>)
    suspend fun getCurrency(currencyCode: String): Currency
    suspend fun fetchCurrencies(selectItem: String): Resource<ExchangeRateResponse>

    companion object {
        const val RELEASE = "release"
        const val DEBUG = "debug"
    }
}