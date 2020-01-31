package com.vbta.currenciesta.data.repository

import com.vbta.currenciesta.data.TrampolineSchedulerRule
import com.vbta.currenciesta.data.source.remote.CurrenciesApi
import com.vbta.currenciesta.data.source.remote.model.CurrenciesRateApi
import com.vbta.currenciesta.domain.model.CurrencyRate
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import java.util.*

class CurrencyRateRepositoryTest {

    @Rule
    @JvmField
    val trampolineSchedulerRule = TrampolineSchedulerRule()

    private val baseCurrency = Currency.getInstance("EUR")
    private val currenciesRateApi = CurrenciesRateApi(
        baseCurrency.currencyCode,
        Date(System.currentTimeMillis()),
        mapOf(
            "USD" to 1.12,
            "GBP" to 0.92,
            "UAH" to 27.2
        )
    )
    private val currenciesApi = mockk<CurrenciesApi> {
        every { getCurrenciesRate(baseCurrency.currencyCode) } returns Single.just(currenciesRateApi)
    }
    private val currencyRateRepository = CurrencyRateRepository(currenciesApi)

    @Test
    fun `getRatesForCurrency should return sorted alphabetically currencies rates`() {
        val expected = listOf(
            CurrencyRate(Currency.getInstance("GBP"), 0.92),
            CurrencyRate(Currency.getInstance("UAH"), 27.2),
            CurrencyRate(Currency.getInstance("USD"), 1.12)
        )

        currencyRateRepository.getRatesForCurrency(baseCurrency)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue(expected)
    }

    @Test
    fun `getRatesForCurrency should return error`() {
        val expectedError = RuntimeException()

        every { currenciesApi.getCurrenciesRate(baseCurrency.currencyCode) } returns Single.error(expectedError)

        currencyRateRepository.getRatesForCurrency(baseCurrency)
            .test()
            .assertError(expectedError)
            .assertNoValues()
    }
}