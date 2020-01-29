package com.vbta.currenciesta.presentation.injection

import com.vbta.currenciesta.presentation.screen.rates.CurrenciesFragment
import com.vbta.currenciesta.presentation.screen.rates.adapter.CurrenciesActions
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presentationModule = module {
    single { provideDecimalFormatter() }
    factory { provideObserveCurrenciesUseCase(get(), get()) }
    scope(named<CurrenciesFragment>()) {
        scoped { (actions: CurrenciesActions) -> provideRatesAdapter(actions, get()) }
    }
    viewModel { provideRatesViewModel(get()) }
}