package com.paypay.currency_converter.di

import android.content.Context
import com.nicoqueijo.android.currencyconverter.kotlin.data.*
import com.paypay.currency_converter.data.AppPrefs
import com.paypay.currency_converter.database.CurrencyDao
import com.paypay.currency_converter.networking.ApiService
import com.paypay.currency_converter.networking.Repository
import com.paypay.currency_converter.networking.RepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        @ApplicationContext context: Context,
        apiService: ApiService,
        currencyDao: CurrencyDao,
        appPrefs: AppPrefs
    ): Repository {
        return RepositoryImplementation(context,apiService,currencyDao, appPrefs)
    }
}