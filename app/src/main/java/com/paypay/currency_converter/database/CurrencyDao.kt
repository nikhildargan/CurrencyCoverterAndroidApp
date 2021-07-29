package com.paypay.currency_converter.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.paypay.currency_converter.model.Currency


@Dao
interface CurrencyDao {
    @Query("SELECT * FROM table_currency WHERE column_currencyCode = :currencyCode")
    suspend fun getCurrency(currencyCode: String): Currency

    @Query("SELECT * FROM table_currency ORDER BY column_currencyCode ASC")
    fun getAllCurrencies(): LiveData<MutableList<Currency>>

    @Query("SELECT * FROM table_currency WHERE column_isSelected = 1 ORDER BY column_order ASC")
    fun getSelectedCurrencies(): LiveData<MutableList<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCurrency(currency: Currency)

    @Transaction
    suspend fun upsertCurrencies(currencies: List<Currency>) {
        currencies.forEach { upsertCurrency(it) }
    }

    @Transaction
    suspend fun updateExchangeRates(currencies: List<Currency>) {
        currencies.forEach { updateExchangeRate(it.currencyCode, it.exchangeRate) }
    }

    @Query("UPDATE table_currency SET column_exchangeRate = :exchangeRate WHERE column_currencyCode = :currencyCode")
    suspend fun updateExchangeRate(currencyCode: String, exchangeRate: Double):Int


}
