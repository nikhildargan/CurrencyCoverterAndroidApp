package com.paypay.currency_converter.viewmodel

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paypay.currency_converter.model.Currency
import com.paypay.currency_converter.model.ExchangeRateResponse
import com.paypay.currency_converter.model.Resource
import com.paypay.currency_converter.networking.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(private val repository: Repository, application: Application):AndroidViewModel(application) {
    val currencyListData by lazy {
        MutableLiveData<MutableList<Currency>>()
    }
    val error by lazy {
        MutableLiveData<String>()
    }
    val currencyCodeList by lazy {
        MutableLiveData<MutableList<String>>()
    }
     val itemPosition = MutableLiveData<Int>()

    // selected item
    private val selectItem
        get() = itemPosition.value?.let {
            currencyCodeList.value?.get(it)
        }
     val isLoading by lazy {
        MutableLiveData(false)
    }
    val defaultText by lazy {
        MutableLiveData("1")
    }
    init {
        defaultText.value="1"
    }
    private var originalCurrencyList:MutableList<Currency>?=null



    fun fetchCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
           when(val result=repository.fetchCurrencies(selectItem?:"")) {
               is Resource.Success -> {
                 var exchangeRateResponse=result.data
                if(exchangeRateResponse?.status==true) {
                    val currencyList=exchangeRateResponse.currencies.toMutableList()
                    originalCurrencyList=currencyList
                    currencyListData.postValue(currencyList)
                    currencyCodeList.postValue(currencyList.map { currency -> currency.trimmedCurrencyCode }.toMutableList())
                    isLoading.postValue(false)
                }
                else {
                    //dummy data in case of error
                    exchangeRateResponse =exchangeRateResponse ?: ExchangeRateResponse(false,null,null,null)
                    originalCurrencyList=exchangeRateResponse.dummyCurrencyList
                    currencyListData.postValue(originalCurrencyList)
                    currencyCodeList.postValue(exchangeRateResponse.currencyCodeList)
                    error.postValue(exchangeRateResponse.errorInfo?.infoMessage)
                    isLoading.postValue(false)
                }
               }
               is Resource.Error->{
                   error.postValue(result.message)
                   isLoading.postValue(false)
                   }

           }
       }
    }

    fun onAmountChanged(s: CharSequence,start: Int,before : Int,
                              count :Int){

        if(s.isNotEmpty()) {
            val x = s.toString().toDouble()
            Log.v("value", "$x"+"st:$start"+"bf:$before")
            currencyListData.value=updateCurrencyData(originalCurrencyList,x)
            print("${currencyListData.value}")
        }

    }

    private fun updateCurrencyData(currencyList: MutableList<Currency>?, x: Double) :MutableList<Currency>{
     val currencyNewList= mutableListOf<Currency>()
        currencyList?.onEach { currency ->
            val currency=Currency(currency.currencyCode,currency.exchangeRate*x)
             currencyNewList.add(currency)
        }
        return currencyNewList
    }


}