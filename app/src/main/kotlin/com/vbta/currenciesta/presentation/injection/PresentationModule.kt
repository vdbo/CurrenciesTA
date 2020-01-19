package com.vbta.currenciesta.presentation.injection

import com.vbta.currenciesta.presentation.screen.rates.RatesFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presentationModule = module {
    viewModel { provideRatesViewModel(get()) }
    scope(named<RatesFragment>()) {
        scoped { provideRatesAdapter(get()) }
        scoped { provideRatesActions(get()) }
    }
}