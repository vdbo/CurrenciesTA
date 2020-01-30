package com.vbta.currenciesta.presentation.screen.currencies

import android.accounts.NetworkErrorException
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.vbta.currenciesta.domain.model.BaseCurrency
import com.vbta.currenciesta.presentation.screen.base.BaseViewModel
import com.vbta.currenciesta.presentation.screen.base.DataState
import com.vbta.currenciesta.presentation.screen.currencies.adapter.CurrencyAmountListItem
import com.vbta.currenciesta.presentation.usecase.ObserveCurrenciesUseCase
import com.vbta.currenciesta.presentation.utils.NetworkState
import com.vbta.currenciesta.presentation.utils.ScrollingState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.*

class CurrenciesViewModel(
    private val observeCurrenciesUseCase: ObserveCurrenciesUseCase,
    private val networkChanges: Observable<NetworkState>
) : BaseViewModel(), CurrenciesFragment.ViewsChanges {

    companion object {
        private val INITIAL_BASE_CURRENCY = CurrencyAmountListItem(
            Currency.getInstance("EUR"),
            100,
            true
        )
    }

    private val _itemsChanges = PublishSubject.create<DataState<List<CurrencyAmountListItem>>>()
    private val _baseCurrencyAmountChanges = BehaviorSubject.createDefault(INITIAL_BASE_CURRENCY)
    private val observeCurrenciesDisposable = CompositeDisposable()
    override val scrollingStateObserver = BehaviorSubject.createDefault(ScrollingState.IDLE)

    val itemsChanges: Observable<DataState<List<CurrencyAmountListItem>>> = _itemsChanges

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onScreenResumed() {
        initCurrenciesObservation()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onScreenPaused() {
        observeCurrenciesDisposable.clear()
    }

    fun onCurrencyClicked(currency: CurrencyAmountListItem) {
        _baseCurrencyAmountChanges.onNext(currency.copy(isBase = true))
    }

    fun onBaseCurrencyAmountChanged(baseCurrency: CurrencyAmountListItem) {
        _baseCurrencyAmountChanges.onNext(baseCurrency)
    }

    private fun initCurrenciesObservation() {
        _itemsChanges.onNext(DataState.Loading)
        observeCurrenciesDisposable += observeCurrenciesUseCase.execute(
            ObserveCurrenciesUseCase.InputData(
                scrollingStateObserver,
                _baseCurrencyAmountChanges.map {
                    BaseCurrency(it.currency, it.amount)
                }
            )
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { _itemsChanges.onNext(DataState.Failed(it)) }
            .takeUntil(networkChanges.filter { it == NetworkState.LOST })
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { _itemsChanges.onNext(DataState.Failed(NetworkErrorException())) }
            .repeatWhen { anys -> anys.flatMap { networkChanges.filter { it == NetworkState.AVAILABLE } } }
            .retryWhen { errors -> errors.flatMap { networkChanges.filter { it == NetworkState.AVAILABLE } } }
            .subscribeBy { _itemsChanges.onNext(DataState.Loaded(it)) }
    }

}
