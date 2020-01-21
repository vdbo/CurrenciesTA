package com.vbta.currenciesta.data.repository

import com.vbta.currenciesta.data.source.remote.CurrenciesApi
import com.vbta.currenciesta.domain.model.CurrencyRate
import com.vbta.currenciesta.domain.repository.CurrencyRateRepositoryContract
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.*

class CurrencyRateRepository(
    private val currenciesApi: CurrenciesApi
) : CurrencyRateRepositoryContract {

    override fun getRatesForCurrency(currency: Currency): Single<List<CurrencyRate>> =
        currenciesApi.getCurrenciesRate(currency.currencyCode)
            .map { result ->
                //TODO change to have right rate
                listOf(CurrencyRate(currency, 0.0))
                    .plus(result.rates.map { CurrencyRate(Currency.getInstance(it.key), it.value) })
            }
            .subscribeOn(Schedulers.io())

}