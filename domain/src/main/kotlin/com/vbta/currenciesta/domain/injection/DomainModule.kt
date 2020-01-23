package com.vbta.currenciesta.domain.injection

import com.vbta.currenciesta.domain.usecase.GetCurrenciesRatesUseCase
import org.koin.dsl.module

val domainModule = module {
    single { GetCurrenciesRatesUseCase(get()) }
}