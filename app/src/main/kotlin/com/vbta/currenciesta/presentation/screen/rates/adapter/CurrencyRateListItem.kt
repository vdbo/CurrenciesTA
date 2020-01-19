package com.vbta.currenciesta.presentation.screen.rates.adapter

data class CurrencyRateListItem(
    val currencyAbbreviation: String,
    val currencyName: String,
    val rate: Float
)