package com.vbta.currenciesta.presentation.screen.rates.adapter

import com.vbta.currenciesta.presentation.screen.base.ListItem
import java.util.*

data class CurrencyAmountListItem(
    val currency: Currency,
    val amount: Number,
    val isBase: Boolean = false,
    override val id: Long = currency.currencyCode.hashCode().toLong()
) : ListItem<Long>