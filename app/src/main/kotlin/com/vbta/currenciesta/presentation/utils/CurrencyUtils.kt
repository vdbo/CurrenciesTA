package com.vbta.currenciesta.presentation.utils

import com.vbta.currenciesta.R
import java.util.*

val Currency.imageRes: Int
    get() = when (this.currencyCode.toLowerCase(Locale.getDefault())) {
        "brl" -> R.drawable.ic_brl
        "chf" -> R.drawable.ic_chf
        "mxn" -> R.drawable.ic_mxn
        "huf" -> R.drawable.ic_huf
        "jpy" -> R.drawable.ic_jpy
        "idr" -> R.drawable.ic_idr
        "isk" -> R.drawable.ic_isk
        "hkd" -> R.drawable.ic_hkd
        "php" -> R.drawable.ic_php
        "cny" -> R.drawable.ic_cny
        "nok" -> R.drawable.ic_nok
        "zar" -> R.drawable.ic_zar
        "aud" -> R.drawable.ic_aud
        "rub" -> R.drawable.ic_rub
        "dkk" -> R.drawable.ic_dkk
        "gbp" -> R.drawable.ic_gbp
        "cad" -> R.drawable.ic_cad
        "myr" -> R.drawable.ic_myr
        "czk" -> R.drawable.ic_czk
        "pln" -> R.drawable.ic_pln
        "ils" -> R.drawable.ic_ils
        "krw" -> R.drawable.ic_krw
        "hrk" -> R.drawable.ic_hrk
        "eur" -> R.drawable.ic_eur
        "thb" -> R.drawable.ic_thb
        "inr" -> R.drawable.ic_inr
        "sek" -> R.drawable.ic_sek
        "bgn" -> R.drawable.ic_bgn
        "sgd" -> R.drawable.ic_sgd
        "ron" -> R.drawable.ic_ron
        "try" -> R.drawable.ic_try
        "nzd" -> R.drawable.ic_nzd
        "usd" -> R.drawable.ic_usd
        else -> R.drawable.ic_cur_placeholder
    }
