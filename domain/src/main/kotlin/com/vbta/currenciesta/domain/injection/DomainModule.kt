package com.vbta.currenciesta.domain.injection

import com.vbta.currenciesta.domain.usecase.ObserveCurrenciesUseCase
import org.koin.dsl.module

val domainModule = module {
    single { ObserveCurrenciesUseCase(get()) }
}