package com.vbta.currenciesta.presentation.injection

import com.bumptech.glide.RequestManager
import com.vbta.currenciesta.presentation.screen.currencies.CurrenciesFragment
import com.vbta.currenciesta.presentation.screen.currencies.adapter.CurrenciesActions
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val QUALIFIER_NETWORK_CHANGES = "network_changes"

val presentationModule = module {
    single(named(QUALIFIER_NETWORK_CHANGES)) { provideNetworkChangesSubject() }
    single { provideNetworkStateListener(get(named(QUALIFIER_NETWORK_CHANGES))) }
    single { provideDecimalFormatter() }
    factory { provideObserveCurrenciesUseCase(get(), get()) }
    scope(named<CurrenciesFragment>()) {
        scoped { (glide: RequestManager, actions: CurrenciesActions) -> provideRatesAdapter(glide, actions, get()) }
    }
    viewModel { provideRatesViewModel(get(), get(named(QUALIFIER_NETWORK_CHANGES))) }
}