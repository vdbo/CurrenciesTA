package com.vbta.currenciesta.presentation.screen.rates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vbta.currenciesta.R
import com.vbta.currenciesta.presentation.screen.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class RatesFragment : BaseFragment<RatesViewModel>() {

    companion object {
        fun newInstance() = RatesFragment()
    }

    override val vm: RatesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.rates_fragment, container, false)

}
