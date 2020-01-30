package com.vbta.currenciesta.presentation.screen.currencies.mapper

import com.vbta.currenciesta.domain.model.CurrencyRate
import com.vbta.currenciesta.presentation.screen.currencies.adapter.CurrencyAmountListItem

fun List<CurrencyRate>.toCurrencyAmountListItem(baseCurrency: CurrencyAmountListItem) = map {
    CurrencyAmountListItem(
        it.currency,
        it.rate * baseCurrency.amount.toDouble(),
        false
    )
}