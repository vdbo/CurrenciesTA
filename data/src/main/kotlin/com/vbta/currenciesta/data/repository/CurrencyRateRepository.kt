package com.vbta.currenciesta.data.repository

import com.vbta.currenciesta.data.source.remote.CurrenciesApi
import com.vbta.currenciesta.domain.model.CurrencyRate
import com.vbta.currenciesta.domain.repository.CurrencyRateRepositoryContract
import io.reactivex.schedulers.Schedulers

class CurrencyRateRepository(
    private val currenciesApi: CurrenciesApi
) : CurrencyRateRepositoryContract {

    override fun getRatesForCurrency(currency: String) =
        currenciesApi.getCurrenciesRate(currency)
            .map { result ->
                result.rates.map { CurrencyRate(it.key, it.value) }
            }
            .subscribeOn(Schedulers.io())

}