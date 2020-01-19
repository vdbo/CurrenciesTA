package com.vbta.currenciesta.presentation.injection

import com.vbta.currenciesta.presentation.screen.rates.RatesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { RatesViewModel(get()) }
}