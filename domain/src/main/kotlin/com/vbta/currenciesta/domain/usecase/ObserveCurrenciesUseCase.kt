package com.vbta.currenciesta.domain.usecase

import com.vbta.currenciesta.domain.model.CurrencyRate
import com.vbta.currenciesta.domain.repository.CurrencyRateRepositoryContract
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit

class ObserveCurrenciesUseCase(
    private val currencyRateRepository: CurrencyRateRepositoryContract
) : UseCase<Currency, Observable<List<CurrencyRate>>> {

    override fun execute(data: Currency): Observable<List<CurrencyRate>> =
        Observable.interval(1, TimeUnit.SECONDS)
            .switchMapSingle {
                currencyRateRepository.getRatesForCurrency(data)
            }

}