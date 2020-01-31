package com.vbta.currenciesta.presentation.injection

import android.text.InputFilter
import com.bumptech.glide.RequestManager
import com.vbta.currenciesta.presentation.screen.currencies.CurrenciesFragment
import com.vbta.currenciesta.presentation.screen.currencies.adapter.CurrenciesActions
import com.vbta.currenciesta.presentation.utils.DecimalDigitsInputFilter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.text.DecimalFormat

const val QUALIFIER_NETWORK_CHANGES = "network_changes"

val presentationModule = module {
    single(named(QUALIFIER_NETWORK_CHANGES)) { provideNetworkChangesSubject() }
    single { provideNetworkStateListener(get(named(QUALIFIER_NETWORK_CHANGES))) }
    single { DecimalFormat("#,###.#####") }
    single<InputFilter> { DecimalDigitsInputFilter(get(), 12, 5) }
    factory { provideObserveCurrenciesUseCase(get(), get()) }
    scope(named<CurrenciesFragment>()) {
        scoped { (glide: RequestManager, actions: CurrenciesActions) ->
            provideRatesAdapter(glide, actions, get(), get())
        }
    }
    viewModel { provideRatesViewModel(get(), get(named(QUALIFIER_NETWORK_CHANGES))) }
}