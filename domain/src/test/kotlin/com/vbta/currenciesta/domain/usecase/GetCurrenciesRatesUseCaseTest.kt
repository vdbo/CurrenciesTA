package com.vbta.currenciesta.domain.usecase

import com.vbta.currenciesta.domain.TrampolineSchedulerRule
import com.vbta.currenciesta.domain.model.BaseCurrency
import com.vbta.currenciesta.domain.model.CurrencyRate
import com.vbta.currenciesta.domain.repository.CurrencyRateRepositoryContract
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import java.util.*

class GetCurrenciesRatesUseCaseTest {

    @Rule
    @JvmField
    val testSchedulerRule = TrampolineSchedulerRule()

    private val currencyRateRepository = mockk<CurrencyRateRepositoryContract>()
    private val getCurrenciesRatesUseCase = GetCurrenciesRatesUseCase(currencyRateRepository)
    private val baseCurrencyData = Currency.getInstance("EUR")
    private val baseCurrency = BaseCurrency(baseCurrencyData, 100)
    private val currencyRates = listOf(
        CurrencyRate(Currency.getInstance("GBP"), 0.9),
        CurrencyRate(Currency.getInstance("UAH"), 23.2),
        CurrencyRate(Currency.getInstance("USD"), 1.2)
    )

    @Test
    fun `onExecute should give base currency with its rates`() {
        val expected = baseCurrency to currencyRates
        every {
            currencyRateRepository.getRatesForCurrency(baseCurrencyData)
        } returns Single.just(currencyRates)

        getCurrenciesRatesUseCase.execute(baseCurrency)
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertResult(expected)
    }

    @Test
    fun `onExecute should give error on repository's error`() {
        val expectedError = RuntimeException()
        every {
            currencyRateRepository.getRatesForCurrency(baseCurrencyData)
        } returns Single.error(expectedError)

        getCurrenciesRatesUseCase.execute(baseCurrency)
            .test()
            .assertNoValues()
            .assertError(expectedError)
    }
}