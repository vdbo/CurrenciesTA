package com.vbta.currenciesta.domain.usecase

import com.vbta.currenciesta.domain.model.CurrencyRate
import com.vbta.currenciesta.domain.repository.CurrencyRateRepositoryContract
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class ObserveCurrencyRateUseCase(
    private val currencyRateRepository: CurrencyRateRepositoryContract
) : UseCase<CurrencyRate, Observable<List<CurrencyRate>>> {

    override fun execute(data: CurrencyRate): Observable<List<CurrencyRate>> =
        //TODO add user's input on base currency
        Observable.interval(1, TimeUnit.SECONDS)
            .switchMapSingle {
                currencyRateRepository.getRatesForCurrency(data.currency)
            }

}