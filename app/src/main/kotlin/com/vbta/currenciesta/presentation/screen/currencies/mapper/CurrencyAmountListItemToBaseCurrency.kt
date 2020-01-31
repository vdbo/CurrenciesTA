package com.vbta.currenciesta.presentation.screen.currencies.mapper

import com.vbta.currenciesta.domain.model.BaseCurrency
import com.vbta.currenciesta.presentation.screen.currencies.adapter.CurrencyAmountListItem

fun CurrencyAmountListItem.toBaseCurrency() = BaseCurrency(currency, amount)