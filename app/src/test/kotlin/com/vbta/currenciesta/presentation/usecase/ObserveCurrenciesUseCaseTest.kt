package com.vbta.currenciesta.presentation.usecase

import com.vbta.currenciesta.domain.model.BaseCurrency
import com.vbta.currenciesta.domain.model.CurrencyRate
import com.vbta.currenciesta.domain.usecase.GetCurrenciesRatesUseCase
import com.vbta.currenciesta.presentation.TrampolineSchedulerRule
import com.vbta.currenciesta.presentation.screen.currencies.adapter.CurrencyAmountListItem
import com.vbta.currenciesta.presentation.utils.ScrollingState
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.DecimalFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ObserveCurrenciesUseCaseTest {

    @Rule
    @JvmField
    val trampolineSchedulerRule = TrampolineSchedulerRule()

    private val testObserver = TestObserver<List<CurrencyAmountListItem>>()
    private val testScheduler = TestScheduler()
    private val baseCurrencyData = Currency.getInstance("EUR")
    private val baseCurrency = BaseCurrency(baseCurrencyData, 100)
    private val currencyRates = listOf(
        CurrencyRate(Currency.getInstance("GBP"), 0.9),
        CurrencyRate(Currency.getInstance("UAH"), 23.2),
        CurrencyRate(Currency.getInstance("USD"), 1.2)
    )
    private val listItems = listOf(
        CurrencyAmountListItem(baseCurrency.currency, baseCurrency.amount, true),
        CurrencyAmountListItem(Currency.getInstance("GBP"), 90.0),
        CurrencyAmountListItem(Currency.getInstance("UAH"), 2320.0),
        CurrencyAmountListItem(Currency.getInstance("USD"), 120.0)
    )
    private val scrollingStateObservable = BehaviorSubject.createDefault(ScrollingState.IDLE)
    private val baseCurrencyObservable = BehaviorSubject.createDefault(baseCurrency)
    private val getCurrenciesRatesUseCase = mockk<GetCurrenciesRatesUseCase> {
        every { execute(baseCurrency) } returns Single.just(baseCurrency to currencyRates)
    }
    private val numberFormat = DecimalFormat("#,###.#####")
    private val observeCurrenciesUseCase = ObserveCurrenciesUseCase(getCurrenciesRatesUseCase, numberFormat)

    @Before
    fun setup() {
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
    }

    @Test
    fun `on new data received should trigger emit`() {
        observeCurrenciesUseCase.execute(
            ObserveCurrenciesUseCase.InputData(scrollingStateObservable, baseCurrencyObservable)
        )
            .subscribeWith(testObserver)

        with(testObserver) {
            assertNotTerminated()
            assertNoErrors()
            assertValueCount(0)
            testScheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS)
            assertValue(listItems)
            dispose()
        }
    }

    @Test
    fun `on new base currency received should trigger emit`() {
        val newBaseCurrency = BaseCurrency(Currency.getInstance("GBP"), 100)
        val oldBaseCurrencyRate = numberFormat.format(1 / currencyRates[0].rate).toDouble() * 100
        val newRatesOnBaseCurrencyChange = baseCurrency to listOf(
            CurrencyRate(Currency.getInstance("EUR"), 1.1),
            CurrencyRate(Currency.getInstance("UAH"), 23.2),
            CurrencyRate(Currency.getInstance("USD"), 1.2)
        )
        observeCurrenciesUseCase.execute(
            ObserveCurrenciesUseCase.InputData(scrollingStateObservable, baseCurrencyObservable)
        ).subscribeWith(testObserver)

        with(testObserver) {
            //Make sure no errors and values on start
            assertNotTerminated()
            assertNoErrors()
            assertValueCount(0)
            //Advance time for 1 sec
            testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
            //Mock triggering of base currency change
            baseCurrencyObservable.onNext(newBaseCurrency)
            //Mock emitting of currencies rates based on base currency change
            every { getCurrenciesRatesUseCase.execute(newBaseCurrency) } returns Single.just(
                newRatesOnBaseCurrencyChange
            )
            //Assert that old items with EUR base currency and new items with GBP base currency were emitted
            assertValues(
                listItems,
                listOf(
                    CurrencyAmountListItem(newBaseCurrency.currency, newBaseCurrency.amount, true),
                    CurrencyAmountListItem(Currency.getInstance("EUR"), oldBaseCurrencyRate),
                    CurrencyAmountListItem(Currency.getInstance("UAH"), 2320.0),
                    CurrencyAmountListItem(Currency.getInstance("USD"), 120.0)
                )
            )
            dispose()
        }
    }

    @Test
    fun `on base currency amount changed should trigger emit`() {
        val updatedBaseCurrency = BaseCurrency(Currency.getInstance("EUR"), 111)
        observeCurrenciesUseCase.execute(
            ObserveCurrenciesUseCase.InputData(scrollingStateObservable, baseCurrencyObservable)
        ).subscribeWith(testObserver)

        with(testObserver) {
            //Make sure no errors and values on start
            assertNotTerminated()
            assertNoErrors()
            assertValueCount(0)
            //Advance time for 1 sec
            testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
            //Mock triggering of base's currency amount change
            baseCurrencyObservable.onNext(updatedBaseCurrency)
            //Assert that old items with one amount of base currency and new items with another one were emitted
            assertValues(
                listItems,
                listOf(
                    CurrencyAmountListItem(updatedBaseCurrency.currency, updatedBaseCurrency.amount, true),
                    CurrencyAmountListItem(Currency.getInstance("GBP"), 99.9),
                    CurrencyAmountListItem(Currency.getInstance("UAH"), 2575.2),
                    CurrencyAmountListItem(Currency.getInstance("USD"), 133.2)
                )
            )
            dispose()
        }
    }

    @Test
    fun `on scrolling state received should stop emitting`() {
        observeCurrenciesUseCase.execute(
            ObserveCurrenciesUseCase.InputData(scrollingStateObservable, baseCurrencyObservable)
        ).subscribeWith(testObserver)

        with(testObserver) {
            //Make sure no errors and values on start
            assertNotTerminated()
            assertNoErrors()
            assertValueCount(0)
            //Advance time for 1 sec
            testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
            //Assert that old items with EUR base currency and new items with GBP base currency were emitted
            assertValues(listItems)
            //Mock triggering of scrolling state changed to SCROLLING
            scrollingStateObservable.onNext(ScrollingState.SCROLLING)
            //Assert that no new lists of items were emitted in 4 seconds
            testScheduler.advanceTimeBy(4000, TimeUnit.MILLISECONDS)
            assertValueCount(1)
            dispose()
        }
    }

    @Test
    fun `on idle state received should continue emitting`() {
        observeCurrenciesUseCase.execute(
            ObserveCurrenciesUseCase.InputData(scrollingStateObservable, baseCurrencyObservable)
        ).subscribeWith(testObserver)

        with(testObserver) {
            //Make sure no errors and values on start
            assertNotTerminated()
            assertNoErrors()
            assertValueCount(0)
            //Advance time for 1 sec
            testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
            //Assert that old items with EUR base currency and new items with GBP base currency were emitted
            assertValues(listItems)
            //Mock triggering of scrolling state changed to SCROLLING
            scrollingStateObservable.onNext(ScrollingState.SCROLLING)
            //Assert that no new items were emitted
            testScheduler.advanceTimeBy(4000, TimeUnit.MILLISECONDS)
            assertValues(listItems)
            //Mock triggering of scrolling state changed to IDLE
            scrollingStateObservable.onNext(ScrollingState.IDLE)
            //Assert that 4 new lists of items were emitted in 4 seconds
            testScheduler.advanceTimeBy(4000, TimeUnit.MILLISECONDS)
            assertValueCount(5)
            dispose()
        }
    }

}