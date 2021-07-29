package com.paypay.currency_converter.model

data class CurrencyDescription(
val currencyCode:String,
val currencyFullDescp:String,
var exchangeRate:Double=0.0
)
