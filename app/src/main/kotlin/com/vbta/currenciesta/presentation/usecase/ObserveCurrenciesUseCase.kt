package com.vbta.currenciesta.presentation.usecase

import android.annotation.SuppressLint
import com.vbta.currenciesta.domain.model.BaseCurrency
import com.vbta.currenciesta.domain.model.CurrencyRate
import com.vbta.currenciesta.domain.usecase.GetCurrenciesRatesUseCase
import com.vbta.currenciesta.domain.usecase.UseCase
import com.vbta.currenciesta.presentation.screen.currencies.adapter.CurrencyAmountListItem
import com.vbta.currenciesta.presentation.screen.currencies.mapper.toCurrencyAmountListItem
import com.vbta.currenciesta.presentation.utils.ScrollingState
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.text.NumberFormat
import java.util.concurrent.TimeUnit

class ObserveCurrenciesUseCase(
    private val getCurrenciesRatesUseCase: GetCurrenciesRatesUseCase,
    private val numberFormat: NumberFormat
) : UseCase<ObserveCurrenciesUseCase.InputData, Observable<List<CurrencyAmountListItem>>> {

    data class InputData(
        val scrollingStateObservable: Observable<ScrollingState>,
        val baseCurrencyObservable: Observable<BaseCurrency>
    )

    companion object {
        private const val INITIAL_DELAY_MS = 500L
        private const val PING_PERIOD_MS = 1000L
    }

    @SuppressLint("CheckResult")
    override fun execute(data: InputData): Observable<List<CurrencyAmountListItem>> {
        val (scrollingStateObservable, baseCurrencyObservable) = data
        var lastBaseCurrency: BaseCurrency? = null

        return Observables.combineLatest(
            baseCurrencyObservable.doOnNext { lastBaseCurrency = it },
            Observable.interval(INITIAL_DELAY_MS, PING_PERIOD_MS, TimeUnit.MILLISECONDS)
                .switchMapSingle {
                    lastBaseCurrency?.let { getCurrenciesRatesUseCase.execute(it) }
                }
        ) { newBaseCurrency, (oldBaseCurrency, rates) ->
            when {
                oldBaseCurrency.currency == newBaseCurrency.currency -> {
                    getCurrencyAmountListItems(newBaseCurrency, rates)
                }
                oldBaseCurrency.currency != newBaseCurrency.currency -> {
                    val newRates = createRatesOnBaseCurrencyChange(rates, newBaseCurrency, oldBaseCurrency)
                    getCurrencyAmountListItems(newBaseCurrency, newRates)
                }
                else -> throw IllegalArgumentException("Inconsistent logical error occurred on comparing base currencies")
            }

        }
            .takeUntil(scrollingStateObservable.filter { it == ScrollingState.SCROLLING })
            .repeatWhen { anys -> anys.flatMap { scrollingStateObservable.filter { it == ScrollingState.IDLE } } }
            .subscribeOn(Schedulers.io())
    }

    private fun createRatesOnBaseCurrencyChange(
        rates: List<CurrencyRate>,
        newBaseCurrency: BaseCurrency,
        oldBaseCurrency: BaseCurrency
    ): ArrayList<CurrencyRate> {
        val currencyRateOfNewBaseCurrency = rates.find { it.currency == newBaseCurrency.currency }
            ?: throw IllegalArgumentException("Old base currency wasn't find in previous rates")
        val rateOfOldBaseCurrency = numberFormat.format(1 / currencyRateOfNewBaseCurrency.rate).toDouble()
        val ratesWithoutNewBaseCurrency = rates.toMutableList()
            .apply { remove(currencyRateOfNewBaseCurrency) }
        return arrayListOf<CurrencyRate>().apply {
            addAll(ratesWithoutNewBaseCurrency)
            add(CurrencyRate(oldBaseCurrency.currency, rateOfOldBaseCurrency))
            sortBy { it.currency.currencyCode }
        }
    }

    private fun getCurrencyAmountListItems(
        newBaseCurrency: BaseCurrency,
        rates: List<CurrencyRate>
    ): List<CurrencyAmountListItem> {
        val baseCurrencyListItem = CurrencyAmountListItem(
            newBaseCurrency.currency,
            newBaseCurrency.amount,
            true
        )
        return arrayListOf<CurrencyAmountListItem>().apply {
            add(baseCurrencyListItem)
            addAll(rates.toCurrencyAmountListItem(baseCurrencyListItem))
        }
    }


}