package com.vbta.currenciesta.domain.repository

import com.vbta.currenciesta.domain.model.CurrencyRate
import io.reactivex.Single
import java.util.*

interface CurrencyRateRepositoryContract {
    fun getRatesForCurrency(currency: Currency): Single<List<CurrencyRate>>
}