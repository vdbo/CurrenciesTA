package com.vbta.currenciesta.presentation.screen.rates

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.vbta.currenciesta.domain.model.CurrencyRate
import com.vbta.currenciesta.domain.usecase.ObserveCurrencyRateUseCase
import com.vbta.currenciesta.presentation.screen.base.BaseViewModel
import com.vbta.currenciesta.presentation.screen.rates.adapter.CurrencyRateListItem
import com.vbta.currenciesta.presentation.screen.rates.adapter.RatesActions
import com.vbta.currenciesta.presentation.screen.rates.mapper.toCurrencyRateListItem
import com.vbta.currenciesta.presentation.utils.ScrollingState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.*

class RatesViewModel(
    private val observeCurrencyRateUseCase: ObserveCurrencyRateUseCase
) : BaseViewModel(), RatesActions, RatesFragment.ViewsChanges {

    private val _itemsChanges = PublishSubject.create<List<CurrencyRateListItem>>()
    private var observeCurrencyDisposable = Disposables.empty()
    override val scrollingStateObserver = PublishSubject.create<ScrollingState>()

    val itemsChanges: Observable<List<CurrencyRateListItem>> = _itemsChanges

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onScreenResumed() {
        initCurrencyRateObservation()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onScreenPaused() {
        observeCurrencyDisposable.dispose()
    }

    override fun onRateClicked(item: CurrencyRateListItem) {
    }

    private fun initCurrencyRateObservation() {
        observeCurrencyDisposable = observeCurrencyRateUseCase.execute(
            CurrencyRate(Currency.getInstance("EUR"), 0f)
        )
            .takeUntil(scrollingStateObserver.filter { it == ScrollingState.SCROLLING })
            .repeatWhen { scrollingStateObserver.filter { it == ScrollingState.IDLE } }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { _itemsChanges.onNext(it.toCurrencyRateListItem()) },
                onError = { Timber.d(it, "Failed observe currency rates") }
            )
    }

}
