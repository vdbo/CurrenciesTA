package com.vbta.currenciesta.data.mapper

import com.vbta.currenciesta.data.source.remote.model.CurrenciesRateApi
import com.vbta.currenciesta.domain.model.CurrencyRate
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.util.*

class CurrenciesRateApiToCurrencyRatesTest {

    private val currenciesRateApi = CurrenciesRateApi(
        "EUR",
        Date(System.currentTimeMillis()),
        mapOf(
            "GBP" to 0.92,
            "UAH" to 27.2,
            "USD" to 1.12
        )
    )

    @Test
    fun `toCurrencyRates should return correct list of CurrencyRate`() {
        val expected = listOf(
            CurrencyRate(Currency.getInstance("GBP"), 0.92),
            CurrencyRate(Currency.getInstance("UAH"), 27.2),
            CurrencyRate(Currency.getInstance("USD"), 1.12)
        )

        assertEquals(expected, currenciesRateApi.toCurrencyRates())
    }
}