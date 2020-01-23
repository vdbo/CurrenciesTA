package com.vbta.currenciesta.presentation.injection

import com.vbta.currenciesta.domain.usecase.GetCurrenciesRatesUseCase
import com.vbta.currenciesta.presentation.screen.rates.CurrenciesViewModel
import com.vbta.currenciesta.presentation.screen.rates.adapter.CurrenciesActions
import com.vbta.currenciesta.presentation.screen.rates.adapter.CurrenciesAdapter
import com.vbta.currenciesta.presentation.usecase.ObserveCurrenciesUseCase

fun provideRatesViewModel(
    observeCurrenciesUseCase: ObserveCurrenciesUseCase
) = CurrenciesViewModel(observeCurrenciesUseCase)

fun provideRatesAdapter(actions: CurrenciesActions) = CurrenciesAdapter(actions)

fun provideObserveCurrenciesUseCase(
    getCurrenciesRatesUseCase: GetCurrenciesRatesUseCase
) = ObserveCurrenciesUseCase(getCurrenciesRatesUseCase)