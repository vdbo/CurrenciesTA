package com.vbta.currenciesta.data.injection

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.vbta.currenciesta.data.repository.CurrencyRateRepository
import com.vbta.currenciesta.data.source.remote.CurrenciesApi
import com.vbta.currenciesta.domain.repository.CurrencyRateRepositoryContract
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

val dataModule = module {
    single { provideMoshi() }
    single { provideRetrofit(get()) }
    single { provideCurrenciesApi(get()) }
    single<CurrencyRateRepositoryContract> { CurrencyRateRepository(get()) }
}

fun provideMoshi(): Moshi = Moshi.Builder()
    .add(Date::class.java, Rfc3339DateJsonAdapter())
    .build()

fun provideRetrofit(moshi: Moshi): Retrofit = Retrofit.Builder()
    .baseUrl("https://revolut.duckdns.org/")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()

fun provideCurrenciesApi(retrofit: Retrofit): CurrenciesApi = retrofit
    .create(CurrenciesApi::class.java)