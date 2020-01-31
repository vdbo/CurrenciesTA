package com.vbta.currenciesta.presentation.injection

import android.text.InputFilter
import com.bumptech.glide.RequestManager
import com.vbta.currenciesta.domain.usecase.GetCurrenciesRatesUseCase
import com.vbta.currenciesta.presentation.screen.currencies.CurrenciesViewModel
import com.vbta.currenciesta.presentation.screen.currencies.adapter.CurrenciesActions
import com.vbta.currenciesta.presentation.screen.currencies.adapter.CurrenciesAdapter
import com.vbta.currenciesta.presentation.usecase.ObserveCurrenciesUseCase
import com.vbta.currenciesta.presentation.utils.NetworkState
import io.reactivex.Observable
import java.text.DecimalFormat

fun provideRatesViewModel(
    observeCurrenciesUseCase: ObserveCurrenciesUseCase,
    networkChanges: Observable<NetworkState>
) = CurrenciesViewModel(observeCurrenciesUseCase, networkChanges)

fun provideRatesAdapter(
    glide: RequestManager,
    actions: CurrenciesActions,
    numberFormat: DecimalFormat,
    inputFilter: InputFilter
) = CurrenciesAdapter(glide, actions, numberFormat, inputFilter)

fun provideObserveCurrenciesUseCase(
    getCurrenciesRatesUseCase: GetCurrenciesRatesUseCase,
    numberFormat: DecimalFormat
) = ObserveCurrenciesUseCase(getCurrenciesRatesUseCase, numberFormat)