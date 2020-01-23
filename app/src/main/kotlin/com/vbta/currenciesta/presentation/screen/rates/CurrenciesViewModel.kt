package com.vbta.currenciesta.presentation.screen.rates

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.vbta.currenciesta.domain.model.BaseCurrency
import com.vbta.currenciesta.presentation.screen.base.BaseViewModel
import com.vbta.currenciesta.presentation.screen.rates.adapter.CurrencyAmountListItem
import com.vbta.currenciesta.presentation.usecase.ObserveCurrenciesUseCase
import com.vbta.currenciesta.presentation.utils.ScrollingState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.*

class CurrenciesViewModel(
    private val observeCurrenciesUseCase: ObserveCurrenciesUseCase
) : BaseViewModel(), CurrenciesFragment.ViewsChanges {

    companion object {
        private val INITIAL_BASE_CURRENCY = CurrencyAmountListItem(
            Currency.getInstance("EUR"),
            100,
            true
        )
    }

    private val _itemsChanges = PublishSubject.create<List<CurrencyAmountListItem>>()
    private val _baseCurrencyAmountChanges = BehaviorSubject.createDefault(INITIAL_BASE_CURRENCY)
    private var observeCurrenciesDisposable = Disposables.empty()
    override val scrollingStateObserver = BehaviorSubject.createDefault(ScrollingState.IDLE)
    override val onListLayoutCompletedObserver = PublishSubject.create<Boolean>()

    val itemsChanges: Observable<List<CurrencyAmountListItem>> = _itemsChanges

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onScreenResumed() {
        initCurrenciesObservation()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onScreenPaused() {
        observeCurrenciesDisposable.dispose()
    }

    fun onCurrencyClicked(item: CurrencyAmountListItem) {
        _baseCurrencyAmountChanges.onNext(item.copy(isBase = true))
    }

    fun onBaseCurrencyAmountChanged(baseCurrency: CurrencyAmountListItem) {
        _baseCurrencyAmountChanges.onNext(baseCurrency)
    }

    private fun initCurrenciesObservation() {
        observeCurrenciesDisposable = observeCurrenciesUseCase.execute(
            ObserveCurrenciesUseCase.InputData(
                scrollingStateObserver,
                _baseCurrencyAmountChanges.map {
                    Timber.d("Debugging Rx: Received base $it")
                    BaseCurrency(it.currency, it.amount)
                }
            )
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { _itemsChanges.onNext(it) },
                onError = { Timber.d(it, "Failed observe currency rates") }
            )
    }

}
