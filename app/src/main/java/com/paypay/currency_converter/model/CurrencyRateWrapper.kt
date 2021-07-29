package com.paypay.currency_converter.model


class CurrencyRateWrapper (private val currencyListModel: CurrencyListModel,private val exchangeRateResponse: ExchangeRateResponse)  {

    /**
     * Creates a list of Currency objects from the declared fields of this class using reflection
     * to instantiate each object's currency code with the declared field's name.
     */
    val currencies: List<Currency>
        get() {
            val currencies = mutableListOf<Currency>()
            currencyListModel.currencyDescpList
            for (currencyDescp in currencyListModel.currencyDescpList) {
                val currencyCode = currencyDescp.currencyCode
                val exchangeRate = exchangeRateResponse.exchangeRateMap?.get(currencyCode) ?:0.0
                currencies.add(Currency(currencyCode, exchangeRate))
            }
            return currencies
        }

    //override fun toString() = currencies.toString()


}
