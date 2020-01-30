package com.vbta.currenciesta.presentation.screen.currencies.adapter

interface CurrenciesActions {
    fun onCurrencyClicked(item: CurrencyAmountListItem)

    fun onBaseCurrencyAmountChanged(currency: CurrencyAmountListItem)
}