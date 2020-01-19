package com.vbta.currenciesta.domain.usecase

import com.vbta.currenciesta.domain.model.CurrencyRate
import com.vbta.currenciesta.domain.repository.CurrencyRateRepositoryContract
import io.reactivex.Observable

class ObserveCurrencyRateUseCase(
    private val currencyRateRepository: CurrencyRateRepositoryContract
) : UseCase<String, Observable<List<CurrencyRate>>> {

    override fun execute(data: String): Observable<List<CurrencyRate>> =
        currencyRateRepository.getRatesForCurrency(data)
            .toObservable()

}