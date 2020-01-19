package com.vbta.currenciesta.data.source.remote

import com.vbta.currenciesta.data.source.remote.model.CurrenciesRateApi
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesApi {

    @GET("latest")
    fun getCurrenciesRate(@Query("base") baseCurrency: String): Single<CurrenciesRateApi>

}