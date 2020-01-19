package com.vbta.currenciesta.presentation.screen.rates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.vbta.currenciesta.R
import com.vbta.currenciesta.presentation.screen.base.BaseFragment
import com.vbta.currenciesta.presentation.screen.rates.adapter.RatesAdapter
import kotlinx.android.synthetic.main.rates_fragment.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class RatesFragment : BaseFragment<RatesViewModel>() {

    companion object {
        fun newInstance() = RatesFragment()
    }

    private val ratesAdapter: RatesAdapter by currentScope.inject()
    override val vm: RatesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.rates_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rates.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rates.adapter = ratesAdapter
    }

}
