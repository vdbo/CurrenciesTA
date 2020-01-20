package com.vbta.currenciesta.presentation.screen.rates.mapper

import com.vbta.currenciesta.domain.model.CurrencyRate
import com.vbta.currenciesta.presentation.screen.rates.adapter.CurrencyRateListItem

fun List<CurrencyRate>.toCurrencyRateListItem() = map {
    CurrencyRateListItem(it.currency, it.rate, false)
}