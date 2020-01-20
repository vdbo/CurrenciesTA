package com.vbta.currenciesta.data.repository

import com.vbta.currenciesta.data.source.remote.CurrenciesApi
import com.vbta.currenciesta.domain.model.CurrencyRate
import com.vbta.currenciesta.domain.repository.CurrencyRateRepositoryContract
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class CurrencyRateRepository(
    private val currenciesApi: CurrenciesApi
) : CurrencyRateRepositoryContract {

    override fun getRatesForCurrency(currency: String): Single<List<CurrencyRate>> =
        currenciesApi.getCurrenciesRate(currency)
            .map { result ->
                //TODO change to have right rate
                listOf(CurrencyRate(currency, 0f))
                    .plus(result.rates.map { CurrencyRate(it.key, it.value) })
            }
            .subscribeOn(Schedulers.io())

}