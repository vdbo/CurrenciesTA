package com.vbta.currenciesta.presentation.screen.rates

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.vbta.currenciesta.domain.usecase.ObserveCurrencyRateUseCase
import com.vbta.currenciesta.presentation.screen.base.BaseViewModel
import com.vbta.currenciesta.presentation.screen.rates.adapter.CurrencyRateListItem
import com.vbta.currenciesta.presentation.screen.rates.adapter.RatesActions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

class RatesViewModel(
    private val observeCurrencyRateUseCase: ObserveCurrencyRateUseCase
) : BaseViewModel(), RatesActions {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onScreenResumed() {
        disposables += observeCurrencyRateUseCase.execute("EUR")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { },
                onError = { Log.d("RatesViewModel", "Failed observe currency rates", it) }
            )
    }

    override fun onRateClicked(item: CurrencyRateListItem) {
    }

}
