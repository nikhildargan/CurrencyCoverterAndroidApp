package com.paypay.currency_converter.model

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class CurrencyTest {

    @Test
    fun sameCurrenciesShouldReturnTrue() {
        val currencyA = Currency("USD_EUR", 0.842993)
        val currencyB = currencyA.copy()
        assertThat(currencyA).isNotSameInstanceAs(currencyB)
        val currencyC = Currency("USD_EUR", 0.842993)
        assertThat(currencyA).isEqualTo(currencyB)
        assertThat(currencyB).isEqualTo(currencyC)
    }

    @Test
    fun currenciesWithSameCurrencyCodeButDifferentExchangeRatesShouldReturnTrue() {
        val currencyA = Currency("USD_EUR", 0.842993)
        val currencyB = Currency("USD_EUR", 0.8641)
        assertThat(currencyA).isEqualTo(currencyB)
    }

    @Test
    fun differentTypeOfCurrencyObjectsShouldReturnFalse() {
        val currencyA = Currency("USD_EUR", 0.842993)
        val currencyB = java.util.Currency.getInstance("EUR")
        assertThat(currencyA).isNotEqualTo(currencyB)
    }

    @Test
    fun currenciesWithDifferentCurrencyCodesButSameExchangeRatesShouldReturnFalse() {
        val currencyA = Currency("USD_USD", 1.0)
        val currencyB = Currency("USD_BSD", 1.0)
        assertThat(currencyA).isNotEqualTo(currencyB)
    }


    @Test
    fun sameCurrencyObjectShouldReturnTrue() {
        val currency = Currency("USD_EUR", 0.842993)
        val areEqual = currency.deepEquals(currency)
        assertThat(areEqual).isTrue()
    }

    @Test
    fun currenciesWithEqualPropertiesShouldReturnTrue() {
        val currencyA = Currency("USD_EUR", 0.842993).apply {
            order = 7
            isSelected = true
        }
        val currencyB = Currency("USD_EUR", 0.842993).apply {
            order = 7
            isSelected = true
        }
        val areEqual = currencyA.deepEquals(currencyB)
        assertThat(areEqual).isTrue()
    }

    @Test
    fun currenciesWithDifferentCurrencyCodePropertyShouldReturnFalse() {
        val currencyA = Currency("USD_USD", 1.0).apply {
            order = 7
            isSelected = true
        }
        val currencyB = Currency("USD_BSD", 1.0).apply {
            order = 7
            isSelected = true
        }
        val areEqual = currencyA.deepEquals(currencyB)
        assertThat(areEqual).isFalse()
    }

    @Test
    fun currenciesWithDifferentExchangeRatePropertyShouldReturnFalse() {
        val currencyA = Currency("USD_EUR", 0.842993).apply {
            order = 7
            isSelected = true
        }
        val currencyB = Currency("USD_EUR", 0.8641).apply {
            order = 7
            isSelected = true
        }
        val areEqual = currencyA.deepEquals(currencyB)
        assertThat(areEqual).isFalse()
    }

    @Test
    fun currenciesWithDifferentIsSelectedPropertyShouldReturnFalse() {
        val currencyA = Currency("USD_EUR", 0.842993).apply {
            order = 7
        }
        val currencyB = Currency("USD_EUR", 0.842993).apply {
            order = 7
        }
        val areEqual = currencyA.deepEquals(currencyB)
        assertThat(areEqual).isFalse()
    }

    @Test
    fun currenciesWithDifferentOrderPropertyShouldReturnFalse() {
        val currencyA = Currency("USD_EUR", 0.842993).apply {
            order = 1
        }
        val currencyB = Currency("USD_EUR", 0.842993).apply {
            order = 2
        }
        val areEqual = currencyA.deepEquals(currencyB)
        assertThat(areEqual).isFalse()
    }


    @Test
    fun trimmedCurrencyCodeShouldReturnCorrectResult() {
        val currency = Currency("USDEUR", 0.842993)
        val expected = "EUR"
        val actual = currency.trimmedCurrencyCode
        assertThat(actual).isEqualTo(expected)
    }


    @Test
    fun currencyShouldReturnCorrectString() {
        val currency = Currency("USDEUR", 0.842993)
        val expected = "{-1 EUR  }"
        val actual = currency.toString()
        assertThat(actual).isEqualTo(expected)
    }

}