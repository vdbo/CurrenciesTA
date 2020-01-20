package com.vbta.currenciesta.presentation.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.vbta.currenciesta.R
import java.util.*

fun Currency.getImage(context: Context) = when (currencyCode.toLowerCase(Locale.getDefault())) {
    "brl" -> ContextCompat.getDrawable(context, R.drawable.ic_brl)
    "chf" -> ContextCompat.getDrawable(context, R.drawable.ic_chf)
    "mxn" -> ContextCompat.getDrawable(context, R.drawable.ic_mxn)
    "huf" -> ContextCompat.getDrawable(context, R.drawable.ic_huf)
    "jpy" -> ContextCompat.getDrawable(context, R.drawable.ic_jpy)
    "idr" -> ContextCompat.getDrawable(context, R.drawable.ic_idr)
    "isk" -> ContextCompat.getDrawable(context, R.drawable.ic_isk)
    "hkd" -> ContextCompat.getDrawable(context, R.drawable.ic_hkd)
    "php" -> ContextCompat.getDrawable(context, R.drawable.ic_php)
    "cny" -> ContextCompat.getDrawable(context, R.drawable.ic_cny)
    "nok" -> ContextCompat.getDrawable(context, R.drawable.ic_nok)
    "zar" -> ContextCompat.getDrawable(context, R.drawable.ic_zar)
    "aud" -> ContextCompat.getDrawable(context, R.drawable.ic_aud)
    "rub" -> ContextCompat.getDrawable(context, R.drawable.ic_rub)
    "dkk" -> ContextCompat.getDrawable(context, R.drawable.ic_dkk)
    "cad" -> ContextCompat.getDrawable(context, R.drawable.ic_cad)
    "myr" -> ContextCompat.getDrawable(context, R.drawable.ic_myr)
    "czk" -> ContextCompat.getDrawable(context, R.drawable.ic_czk)
    "pln" -> ContextCompat.getDrawable(context, R.drawable.ic_pln)
    "ils" -> ContextCompat.getDrawable(context, R.drawable.ic_ils)
    "krw" -> ContextCompat.getDrawable(context, R.drawable.ic_krw)
    "hrk" -> ContextCompat.getDrawable(context, R.drawable.ic_hrk)
    "eur" -> ContextCompat.getDrawable(context, R.drawable.ic_eur)
    "thb" -> ContextCompat.getDrawable(context, R.drawable.ic_thb)
    "inr" -> ContextCompat.getDrawable(context, R.drawable.ic_inr)
    "sek" -> ContextCompat.getDrawable(context, R.drawable.ic_sek)
    "bgn" -> ContextCompat.getDrawable(context, R.drawable.ic_bgn)
    "sgd" -> ContextCompat.getDrawable(context, R.drawable.ic_sgd)
    "ron" -> ContextCompat.getDrawable(context, R.drawable.ic_ron)
    "try" -> ContextCompat.getDrawable(context, R.drawable.ic_try)
    "nzd" -> ContextCompat.getDrawable(context, R.drawable.ic_nzd)
    "usd" -> ContextCompat.getDrawable(context, R.drawable.ic_usd)
    else -> ContextCompat.getDrawable(context, android.R.drawable.btn_default)
}