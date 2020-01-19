package com.vbta.currenciesta.domain.injection

import com.vbta.currenciesta.domain.usecase.ObserveCurrencyRateUseCase
import org.koin.dsl.module

val domainModule = module {
    single { ObserveCurrencyRateUseCase(get()) }
}