package com.vbta.currenciesta.presentation.utils

import android.view.View

fun View.forceRequestFocus() {
    isFocusableInTouchMode = true
    requestFocus()
    isFocusableInTouchMode = false
}