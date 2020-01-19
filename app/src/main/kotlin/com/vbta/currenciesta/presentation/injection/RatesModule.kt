package com.vbta.currenciesta.presentation.injection

import com.vbta.currenciesta.domain.usecase.ObserveCurrencyRateUseCase
import com.vbta.currenciesta.presentation.screen.rates.RatesViewModel
import com.vbta.currenciesta.presentation.screen.rates.adapter.RatesActions
import com.vbta.currenciesta.presentation.screen.rates.adapter.RatesAdapter

fun provideRatesViewModel(
    observeCurrencyRateUseCase: ObserveCurrencyRateUseCase
) = RatesViewModel(observeCurrencyRateUseCase)

fun provideRatesAdapter(ratesActions: RatesActions) = RatesAdapter(ratesActions)

fun provideRatesActions(viewModel: RatesViewModel): RatesActions = viewModel