package com.vbta.currenciesta.data.source.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class CurrenciesRateApi(
    @Json(name = "base")
    val base: String,
    @Json(name = "date")
    val date: Date,
    @Json(name = "rates")
    val rates: Map<String, Float>
)