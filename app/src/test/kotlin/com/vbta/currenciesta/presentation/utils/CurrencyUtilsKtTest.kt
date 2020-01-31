package com.vbta.currenciesta.presentation.utils

import com.vbta.currenciesta.R
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class CurrencyUtilsKtTest {

    private val currencyCode = "EUR"

    @Test
    fun `getImageRes should return correct flag`() {
        assertEquals(R.drawable.ic_eur, Currency.getInstance(currencyCode).imageRes)
    }
}