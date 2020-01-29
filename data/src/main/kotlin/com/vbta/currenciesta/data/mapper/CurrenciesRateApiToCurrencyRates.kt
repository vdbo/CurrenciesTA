package com.vbta.currenciesta.data.mapper

import com.vbta.currenciesta.data.source.remote.model.CurrenciesRateApi
import com.vbta.currenciesta.domain.model.CurrencyRate
import java.util.*

fun CurrenciesRateApi.toCurrencyRates() = rates.map { CurrencyRate(Currency.getInstance(it.key), it.value) }