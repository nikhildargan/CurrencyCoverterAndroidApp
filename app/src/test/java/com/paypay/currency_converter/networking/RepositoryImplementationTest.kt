package com.paypay.currency_converter.networking

import android.content.Context
import com.google.common.truth.Truth.assertThat
import com.paypay.currency_converter.data.AppPrefs
import com.paypay.currency_converter.database.CurrencyDao
import com.paypay.currency_converter.model.Resource
import com.paypay.currency_converter.util.Utils.isNetworkAvailable
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class RepositoryImplementationTest {

    private val context = mockk<Context>(relaxed = true)
    private val apiService = mockk<ApiService>(relaxed = true)
    private val currencyDao = mockk<CurrencyDao>(relaxed = true)
    private val appPrefs = mockk<AppPrefs>(relaxed = true)
    private val repository: Repository = RepositoryImplementation(
            context,
            apiService,
            currencyDao,
            appPrefs
    )

        @Test
        fun whenNetworkIsAvailableAndDataIsNotEmptyAndDataIsNotStaleShouldReturnSuccess() = runBlocking {
            every { context.isNetworkAvailable() } returns true
            every { appPrefs.isDataEmpty } returns false
            every { appPrefs.isDataStale } returns false
            val expected = Resource.Success(null)
            val actual = repository.fetchCurrencies("")
            assertThat(actual).isInstanceOf(expected.javaClass)
        }

        @Test
        fun whenNetworkIsUnavailableAndDataIsEmptyShouldReturnError() = runBlocking {
            every { context.isNetworkAvailable() } returns false
            every { appPrefs.isDataEmpty } returns true
            every { appPrefs.isDataStale } returns false
            val expected = Resource.Error<String>(RepositoryImplementation.NETWORK_OR_DATA_UNAVAILABLE_ERROR_MESSAGE)
            val actual = repository.fetchCurrencies("")
            assertThat(actual).isInstanceOf(expected.javaClass)
            assertThat((actual as Resource.Error).message).isEqualTo(expected.message)
        }

        @Test
        fun whenNetworkIsUnavailableAndDataIsNotEmptyShouldReturnSuccess() = runBlocking {
            every { context.isNetworkAvailable() } returns false
            every { appPrefs.isDataEmpty } returns false
            every { appPrefs.isDataStale } returns true
            val expected = Resource.Success(null)
            val actual = repository.fetchCurrencies("")
            assertThat(actual).isInstanceOf(expected.javaClass)
        }


}


