package com.vbta.currenciesta.presentation.injection

import com.vbta.currenciesta.domain.usecase.GetCurrenciesRatesUseCase
import com.vbta.currenciesta.presentation.screen.currencies.CurrenciesViewModel
import com.vbta.currenciesta.presentation.screen.currencies.adapter.CurrenciesActions
import com.vbta.currenciesta.presentation.screen.currencies.adapter.CurrenciesAdapter
import com.vbta.currenciesta.presentation.usecase.ObserveCurrenciesUseCase
import com.vbta.currenciesta.presentation.utils.NetworkState
import io.reactivex.Observable
import java.text.DecimalFormat
import java.text.NumberFormat

fun provideRatesViewModel(
    observeCurrenciesUseCase: ObserveCurrenciesUseCase,
    networkChanges: Observable<NetworkState>
) = CurrenciesViewModel(observeCurrenciesUseCase, networkChanges)

fun provideRatesAdapter(
    actions: CurrenciesActions,
    numberFormat: NumberFormat
) = CurrenciesAdapter(actions, numberFormat)

fun provideObserveCurrenciesUseCase(
    getCurrenciesRatesUseCase: GetCurrenciesRatesUseCase,
    numberFormat: NumberFormat
) = ObserveCurrenciesUseCase(getCurrenciesRatesUseCase, numberFormat)

fun provideDecimalFormatter(): NumberFormat = DecimalFormat("#,###.#####")