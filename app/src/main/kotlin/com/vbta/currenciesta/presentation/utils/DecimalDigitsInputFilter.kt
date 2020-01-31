package com.vbta.currenciesta.presentation.utils

import android.text.InputFilter
import android.text.Spanned
import java.text.DecimalFormat

class DecimalDigitsInputFilter(
    private val format: DecimalFormat,
    private val onesMaxNumber: Int,
    private val decimalsMaxNumber: Int
) : InputFilter {

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        if (dest.isEmpty() || source.isEmpty()) return null
        if (source[0] == '.' || source[0] == format.decimalFormatSymbols.groupingSeparator) return null
        val decimalSymbolPosition = dest.indexOfLast { it == '.' }

        return if (decimalSymbolPosition == -1) {
            if (end + dest.length > onesMaxNumber) "" else null
        } else {
            val groupSymbolsAmount = dest.count { it == format.decimalFormatSymbols.groupingSeparator }
            val decimalsAmount = dest.length - decimalSymbolPosition - 1
            val onesAmount = decimalSymbolPosition - groupSymbolsAmount
            when {
                dstart <= decimalSymbolPosition && end + onesAmount > onesMaxNumber -> ""
                dstart > decimalSymbolPosition && end + decimalsAmount > decimalsMaxNumber -> ""
                else -> null
            }
        }
    }

}