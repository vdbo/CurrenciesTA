package com.vbta.currenciesta.presentation.screen.currencies

import com.vbta.currenciesta.presentation.TrampolineSchedulerRule
import com.vbta.currenciesta.presentation.screen.base.DataState
import com.vbta.currenciesta.presentation.screen.currencies.adapter.CurrencyAmountListItem
import com.vbta.currenciesta.presentation.usecase.ObserveCurrenciesUseCase
import com.vbta.currenciesta.presentation.utils.NetworkState
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.junit.Rule
import org.junit.Test
import java.util.*

class CurrenciesViewModelTest {

    @Rule
    @JvmField
    val trampolineSchedulerRule = TrampolineSchedulerRule()

    private val testObserver = TestObserver<DataState<List<CurrencyAmountListItem>>>()
    private val currenciesAmountListItems = listOf(
        CurrencyAmountListItem(Currency.getInstance("EUR"), 100),
        CurrencyAmountListItem(Currency.getInstance("GBP"), 92.0),
        CurrencyAmountListItem(Currency.getInstance("UAH"), 2722.0),
        CurrencyAmountListItem(Currency.getInstance("USD"), 120.0)
    )
    private val networkChanges = PublishSubject.create<NetworkState>()
    private val observeCurrenciesUseCase = mockk<ObserveCurrenciesUseCase> {
        every {
            execute(any())
        } returns Observable.create { emitter ->
            emitter.onNext(currenciesAmountListItems)
        }
    }
    private val currenciesViewModel = CurrenciesViewModel(observeCurrenciesUseCase, networkChanges)

    @Test
    fun `on new currencies rates getItemsChanges should return loaded data state with list items`() {
        currenciesViewModel.itemsChanges
            .subscribeWith(testObserver)

        currenciesViewModel.onScreenResumed()

        testObserver.assertNoErrors()
            .assertValues(
                DataState.Loading,
                DataState.Loaded(currenciesAmountListItems)
            )
    }

    @Test
    fun `on network error getItemsChanges should return failed data state`() {
        val expectedError = RuntimeException()
        every { observeCurrenciesUseCase.execute(any()) } returns Observable.error(expectedError)
        currenciesViewModel.itemsChanges
            .subscribeWith(testObserver)

        currenciesViewModel.onScreenResumed()

        testObserver.assertNoErrors()
            .assertValues(
                DataState.Loading,
                DataState.Failed(expectedError)
            )
    }

    @Test
    fun `on network lost getItemsChanges should return failed data state`() {
        currenciesViewModel.itemsChanges
            .subscribeWith(testObserver)

        currenciesViewModel.onScreenResumed()

        networkChanges.onNext(NetworkState.Lost)
        testObserver.assertNoErrors()
            .assertValues(
                DataState.Loading,
                DataState.Loaded(currenciesAmountListItems),
                DataState.Failed(NetworkState.Lost.error)
            )
    }

    @Test
    fun `on network availability, after network lost, getItemsChanges should return loaded data state with new list items`() {
        currenciesViewModel.itemsChanges
            .subscribeWith(testObserver)

        currenciesViewModel.onScreenResumed()

        networkChanges.onNext(NetworkState.Lost)
        networkChanges.onNext(NetworkState.Available)
        testObserver.assertNoErrors()
            .assertValues(
                DataState.Loading,
                DataState.Loaded(currenciesAmountListItems),
                DataState.Failed(NetworkState.Lost.error),
                DataState.Loaded(currenciesAmountListItems)
            )
    }
}