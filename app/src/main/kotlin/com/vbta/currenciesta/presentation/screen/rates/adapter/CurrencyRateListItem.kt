package com.vbta.currenciesta.presentation.screen.rates.adapter

import com.vbta.currenciesta.presentation.screen.base.ListItem

data class CurrencyRateListItem(
    val currencyAbbreviation: String,
    val currencyName: String,
    val rate: Float,
    val isBase: Boolean = false,
    override val id: Long = currencyAbbreviation.hashCode().toLong()
) : ListItem<Long>