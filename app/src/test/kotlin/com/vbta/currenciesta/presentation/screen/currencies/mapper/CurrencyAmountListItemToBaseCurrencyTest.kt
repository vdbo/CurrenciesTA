package com.vbta.currenciesta.presentation.screen.currencies.mapper

import com.vbta.currenciesta.domain.model.BaseCurrency
import com.vbta.currenciesta.presentation.screen.currencies.adapter.CurrencyAmountListItem
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class CurrencyAmountListItemToBaseCurrencyTest {


    private val listItem = CurrencyAmountListItem(Currency.getInstance("EUR"), 100, true)

    @Test
    fun `toBaseCurrency should return correct base currency`() {
        val expected = BaseCurrency(Currency.getInstance("EUR"), 100)

        assertEquals(expected, listItem.toBaseCurrency())
    }
}