package com.vbta.currenciesta.presentation.screen.rates.mapper

import com.vbta.currenciesta.domain.model.CurrencyRate
import com.vbta.currenciesta.presentation.screen.rates.adapter.CurrencyAmountListItem

fun List<CurrencyRate>.toCurrencyRateListItem(baseCurrencyAmount: Double) = map {
    CurrencyAmountListItem(it.currency, it.rate * baseCurrencyAmount, false)
}