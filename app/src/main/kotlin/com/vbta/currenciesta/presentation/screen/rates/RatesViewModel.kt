package com.vbta.currenciesta.presentation.screen.rates

import com.vbta.currenciesta.domain.usecase.ObserveCurrencyRateUseCase
import com.vbta.currenciesta.presentation.screen.base.BaseViewModel
import com.vbta.currenciesta.presentation.screen.rates.adapter.CurrencyRateListItem
import com.vbta.currenciesta.presentation.screen.rates.adapter.RatesActions
import com.vbta.currenciesta.presentation.screen.rates.mapper.toCurrencyRateListItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.*

class RatesViewModel(
    observeCurrencyRateUseCase: ObserveCurrencyRateUseCase
) : BaseViewModel(), RatesActions {

    private val _itemsChanges = PublishSubject.create<List<CurrencyRateListItem>>()
    val itemsChanges: Observable<List<CurrencyRateListItem>> = _itemsChanges

    init {
        disposables += observeCurrencyRateUseCase.execute(Currency.getInstance("EUR"))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { _itemsChanges.onNext(it.toCurrencyRateListItem()) },
                onError = { Timber.d(it, "Failed observe currency rates") }
            )
    }

    override fun onRateClicked(item: CurrencyRateListItem) {
    }

}
