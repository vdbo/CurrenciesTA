package com.vbta.currenciesta.presentation.injection

import com.vbta.currenciesta.domain.usecase.GetCurrenciesRatesUseCase
import com.vbta.currenciesta.presentation.screen.rates.CurrenciesViewModel
import com.vbta.currenciesta.presentation.screen.rates.adapter.CurrenciesActions
import com.vbta.currenciesta.presentation.screen.rates.adapter.CurrenciesAdapter
import com.vbta.currenciesta.presentation.usecase.ObserveCurrenciesUseCase
import java.text.DecimalFormat
import java.text.NumberFormat

fun provideRatesViewModel(
    observeCurrenciesUseCase: ObserveCurrenciesUseCase
) = CurrenciesViewModel(observeCurrenciesUseCase)

fun provideRatesAdapter(
    actions: CurrenciesActions,
    numberFormat: NumberFormat
) = CurrenciesAdapter(actions, numberFormat)

fun provideObserveCurrenciesUseCase(
    getCurrenciesRatesUseCase: GetCurrenciesRatesUseCase,
    numberFormat: NumberFormat
) = ObserveCurrenciesUseCase(getCurrenciesRatesUseCase, numberFormat)

fun provideDecimalFormatter(): NumberFormat = DecimalFormat("#,###.#####")