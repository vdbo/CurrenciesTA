package com.vbta.currenciesta.presentation.screen.rates.adapter

interface CurrenciesActions {
    fun onCurrencyClicked(item: CurrencyAmountListItem)

    fun onBaseCurrencyAmountChanged(currency: CurrencyAmountListItem)
}