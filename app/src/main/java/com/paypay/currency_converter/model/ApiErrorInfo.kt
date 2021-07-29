package com.paypay.currency_converter.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiErrorInfo(
    @Json(name = "code")
    val code:Int,
    @Json(name="info")
    val infoMessage:String

)
