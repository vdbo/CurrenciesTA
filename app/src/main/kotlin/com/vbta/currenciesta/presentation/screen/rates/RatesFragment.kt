package com.vbta.currenciesta.presentation.screen.rates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.vbta.currenciesta.R
import com.vbta.currenciesta.presentation.screen.base.BaseFragment
import com.vbta.currenciesta.presentation.screen.rates.adapter.CurrencyRateListItem
import com.vbta.currenciesta.presentation.screen.rates.adapter.RatesAdapter
import com.vbta.currenciesta.presentation.utils.ScrollStateChangeListener
import com.vbta.currenciesta.presentation.utils.ScrollingState
import io.reactivex.Observer
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.rates_fragment.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class RatesFragment : BaseFragment<RatesViewModel>() {

    interface ViewsChanges {
        val scrollingStateObserver: Observer<ScrollingState>
        val baseCurrencyObserver: Observer<CurrencyRateListItem>
    }

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
        with(rates) {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = ratesAdapter
            setHasFixedSize(true)
            addOnScrollListener(ScrollStateChangeListener(vm.scrollingStateObserver))
        }
    }

    override fun onResume() {
        super.onResume()
        disposables += vm.itemsChanges.subscribe(ratesAdapter::setItems)
    }

}
