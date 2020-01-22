package com.vbta.currenciesta.presentation.screen.rates

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.vbta.currenciesta.domain.usecase.ObserveCurrenciesUseCase
import com.vbta.currenciesta.presentation.screen.base.BaseViewModel
import com.vbta.currenciesta.presentation.screen.rates.adapter.CurrencyAmountListItem
import com.vbta.currenciesta.presentation.screen.rates.mapper.toCurrencyRateListItem
import com.vbta.currenciesta.presentation.utils.ScrollingState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.*

class CurrenciesViewModel(
    private val observeCurrenciesUseCase: ObserveCurrenciesUseCase
) : BaseViewModel(), CurrenciesFragment.ViewsChanges {

    private val _itemsChanges = PublishSubject.create<List<CurrencyAmountListItem>>()
    private val _baseCurrencyAmountChanges = BehaviorSubject.createDefault<Double>(1.0)
    private var observeCurrencyDisposable = Disposables.empty()
    override val scrollingStateObserver = PublishSubject.create<ScrollingState>()

    val itemsChanges: Observable<List<CurrencyAmountListItem>> = _itemsChanges

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onScreenResumed() {
        initCurrencyRateObservation()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onScreenPaused() {
        observeCurrencyDisposable.dispose()
    }

    fun onCurrencyClicked(item: CurrencyAmountListItem) {
    }

    fun onBaseCurrencyAmountChanged(amount: Double) {
        _baseCurrencyAmountChanges.onNext(amount)
    }

    private fun initCurrencyRateObservation() {
        observeCurrencyDisposable = Observables.combineLatest(
            _baseCurrencyAmountChanges,
            observeCurrenciesUseCase.execute(Currency.getInstance("EUR"))
        ) { baseCurrencyAmount, rates ->
            listOf(
                CurrencyAmountListItem(Currency.getInstance("EUR"), baseCurrencyAmount, true)
            ).plus(
                rates.toCurrencyRateListItem(baseCurrencyAmount)
            )
        }
            .takeUntil(scrollingStateObserver.filter { it == ScrollingState.SCROLLING })
            .repeatWhen { scrollingStateObserver.filter { it == ScrollingState.IDLE } }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { _itemsChanges.onNext(it) },
                onError = { Timber.d(it, "Failed observe currency rates") }
            )
    }

}
