package com.vbta.currenciesta.presentation.injection

import com.vbta.currenciesta.domain.usecase.ObserveCurrenciesUseCase
import com.vbta.currenciesta.presentation.screen.rates.CurrenciesViewModel
import com.vbta.currenciesta.presentation.screen.rates.adapter.CurrenciesActions
import com.vbta.currenciesta.presentation.screen.rates.adapter.CurrenciesAdapter

fun provideRatesViewModel(
    observeCurrenciesUseCase: ObserveCurrenciesUseCase
) = CurrenciesViewModel(observeCurrenciesUseCase)

fun provideRatesAdapter(actions: CurrenciesActions) = CurrenciesAdapter(actions)