package com.vbta.currenciesta.presentation.screen.currencies.mapper

import com.vbta.currenciesta.domain.model.CurrencyRate
import com.vbta.currenciesta.presentation.screen.currencies.adapter.CurrencyAmountListItem
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class CurrencyRateToCurrencyAmountListItemTest {

    private val baseCurrency = CurrencyAmountListItem(Currency.getInstance("EUR"), 100, true)
    private val currenciesRates = listOf(
        CurrencyRate(Currency.getInstance("GBP"), 0.92),
        CurrencyRate(Currency.getInstance("UAH"), 27.22),
        CurrencyRate(Currency.getInstance("USD"), 1.2)
    )

    @Test
    fun `toCurrencyAmountListItem should return correct list of CurrencyAmountListItem`() {
        val expected = listOf(
            CurrencyAmountListItem(Currency.getInstance("GBP"), 92.0),
            CurrencyAmountListItem(Currency.getInstance("UAH"), 2722.0),
            CurrencyAmountListItem(Currency.getInstance("USD"), 120.0)
        )

        assertEquals(expected, currenciesRates.toCurrencyAmountListItem(baseCurrency))
    }
}