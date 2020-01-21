package com.vbta.currenciesta.domain.model

import java.util.*

data class CurrencyRate(
    val currency: Currency,
    val rate: Double
)