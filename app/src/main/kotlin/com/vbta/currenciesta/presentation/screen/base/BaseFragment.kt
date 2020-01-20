package com.vbta.currenciesta.presentation.screen.base

import android.content.Context
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    protected val disposables = CompositeDisposable()
    protected abstract val vm: T

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycle.addObserver(vm)
    }

    override fun onPause() {
        super.onPause()
        disposables.clear()
    }

}