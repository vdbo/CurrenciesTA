package com.vbta.currenciesta.domain.usecase

import com.vbta.currenciesta.domain.model.BaseCurrency
import com.vbta.currenciesta.domain.model.CurrencyRate
import com.vbta.currenciesta.domain.repository.CurrencyRateRepositoryContract
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class GetCurrenciesRatesUseCase(
    private val currencyRateRepository: CurrencyRateRepositoryContract
) : UseCase<BaseCurrency, Single<Pair<BaseCurrency, List<CurrencyRate>>>> {

    override fun execute(data: BaseCurrency): Single<Pair<BaseCurrency, List<CurrencyRate>>> =
        currencyRateRepository.getRatesForCurrency(data.currency)
            .map { rates -> data to rates }
            .subscribeOn(Schedulers.io())
}