package com.paypay.currency_converter.networking

import android.content.Context
import com.paypay.currency_converter.data.AppPrefs
import com.paypay.currency_converter.database.CurrencyDao
import com.paypay.currency_converter.model.Currency
import com.paypay.currency_converter.model.CurrencyListModel
import com.paypay.currency_converter.model.ExchangeRateResponse
import com.paypay.currency_converter.model.Resource
import com.paypay.currency_converter.util.Utils.isNetworkAvailable
import kotlinx.coroutines.*
import retrofit2.Response
import java.net.SocketTimeoutException

class RepositoryImplementation (
    private val context: Context,
    private val apiService: ApiService,
    private val currencyDao: CurrencyDao,
    private val appPrefs: AppPrefs
        ): Repository {
    override var isFirstLaunch = appPrefs.isFirstLaunch

    override val timestampInSeconds = appPrefs.timestampInSeconds

    override fun getAllCurrencies()=currencyDao.getAllCurrencies()

    override fun getSelectedCurrencies()=currencyDao.getSelectedCurrencies()

    override fun upsertCurrency(currency: Currency) {
        CoroutineScope(Dispatchers.IO).launch {
            currencyDao.upsertCurrency(currency)
        }
    }

    override fun upsertCurrencies(currencies: List<Currency>) {
        CoroutineScope(Dispatchers.IO).launch {
            currencyDao.upsertCurrencies(currencies)
        }
    }

    override suspend fun getCurrency(currencyCode: String)=currencyDao.getCurrency(currencyCode)

    @ExperimentalCoroutinesApi
    override suspend fun fetchCurrencies(selectItem: String): Resource<ExchangeRateResponse> {
        var exchangeRateResponse: Response<ExchangeRateResponse>?=null
        if (context.isNetworkAvailable() && (appPrefs.isDataEmpty || appPrefs.isDataStale)) {
            val currencyListJob:Deferred<Response<CurrencyListModel>>
            try {
                 coroutineScope {
                     currencyListJob = async {
                         apiService.getCurrencyList(API_KEY)
                     }
                 if (currencyListJob.await().isSuccessful)
                 {
                     val currencyListResponse=currencyListJob.getCompleted()
                     val currencies:String?=getCurrenciesFromResponse(currencyListResponse)
                     if (currencies!=null && currencies.isNotEmpty()) {
                         exchangeRateResponse=async{apiService.getExchangeRates(API_KEY, currencies,selectItem)}.await()
                     }
                 }
                 }
            } catch (e: SocketTimeoutException) {
                return Resource.Error(NETWORK_TIMEOUT_ERROR_MESSAGE)
            }
            catch (e:Exception)
            {
                return Resource.Error(e.localizedMessage)
            }
            return if (exchangeRateResponse?.isSuccessful==true) {
                persistResponse(exchangeRateResponse)
                Resource.Success(exchangeRateResponse?.body())
            } else {
                Resource.Error(exchangeRateResponse?.errorBody()?.toString())
            }
        } else if (!appPrefs.isDataEmpty) {
            return Resource.Success(exchangeRateResponse?.body())
        } else {
            return Resource.Error(NETWORK_OR_DATA_UNAVAILABLE_ERROR_MESSAGE)
        }
    }
    private suspend fun persistResponse(exchangeRateResponse: Response<ExchangeRateResponse>?) {
        exchangeRateResponse?.body()?.let { responseBody ->
            responseBody.exchangeRateMap?.let { exchangeRates ->
                persistCurrencies(responseBody)
            }
            appPrefs.timestampInSeconds = responseBody.timestamp?:0
        }
    }

    private suspend fun persistCurrencies(exchangeRates: ExchangeRateResponse) {
        when {
            appPrefs.isDataEmpty -> currencyDao.upsertCurrencies(exchangeRates.currencies)
            appPrefs.isDataStale -> currencyDao.updateExchangeRates(exchangeRates.currencies)
        }
    }


    private fun getCurrenciesFromResponse(response: Response<CurrencyListModel>): String? {
      val currencyListModel=response.body()
        return if(currencyListModel?.status==true && currencyListModel.currencyMap.isNotEmpty())
               currencyListModel.currencyCode
        else
            null
    }

    companion object{
        const val API_KEY="3927db2e7e14d00d8906ff6cb907ac23"
        const val NETWORK_OR_DATA_UNAVAILABLE_ERROR_MESSAGE = "Network is unavailable and no local data found."
        const val NETWORK_TIMEOUT_ERROR_MESSAGE = "Network request timed out."
    }
}