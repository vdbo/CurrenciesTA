package com.vbta.currenciesta.presentation.screen

import android.content.Context
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    protected abstract val vm: T

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycle.addObserver(vm)
    }

}