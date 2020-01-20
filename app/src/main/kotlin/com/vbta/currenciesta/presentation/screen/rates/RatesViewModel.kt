package com.vbta.currenciesta.presentation.screen.rates

import com.vbta.currenciesta.domain.usecase.ObserveCurrencyRateUseCase
import com.vbta.currenciesta.presentation.screen.base.BaseViewModel
import com.vbta.currenciesta.presentation.screen.rates.adapter.CurrencyRateListItem
import com.vbta.currenciesta.presentation.screen.rates.adapter.RatesActions
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

class RatesViewModel(
    observeCurrencyRateUseCase: ObserveCurrencyRateUseCase
) : BaseViewModel(), RatesActions {

    private val _itemsChanges = PublishSubject.create<List<CurrencyRateListItem>>()
    val itemsChanges: Observable<List<CurrencyRateListItem>> = _itemsChanges

    init {
        disposables += observeCurrencyRateUseCase.execute("EUR")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { rates ->
                    _itemsChanges.onNext(
                        //TODO move to mapper method
                        rates.mapIndexed { index, currencyRate ->
                            CurrencyRateListItem(
                                currencyRate.currency,
                                "no name",
                                currencyRate.rate,
                                index == 0
                            )
                        }
                    )
                },
                onError = { Timber.d(it, "Failed observe currency rates") }
            )
    }

    override fun onRateClicked(item: CurrencyRateListItem) {
    }

}
