package com.vbta.currenciesta.domain.repository

import com.vbta.currenciesta.domain.model.CurrencyRate
import io.reactivex.Single

interface CurrencyRateRepositoryContract {
    fun getRatesForCurrency(currency: String): Single<List<CurrencyRate>>
}