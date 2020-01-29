package com.vbta.currenciesta.presentation.screen.rates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.vbta.currenciesta.R
import com.vbta.currenciesta.presentation.screen.base.BaseFragment
import com.vbta.currenciesta.presentation.screen.rates.adapter.CurrenciesActions
import com.vbta.currenciesta.presentation.screen.rates.adapter.CurrenciesAdapter
import com.vbta.currenciesta.presentation.screen.rates.adapter.CurrencyAmountListItem
import com.vbta.currenciesta.presentation.utils.ScrollStateChangeListener
import com.vbta.currenciesta.presentation.utils.ScrollingState
import io.reactivex.Observer
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.currencies_fragment.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CurrenciesFragment : BaseFragment<CurrenciesViewModel>(), CurrenciesActions {

    interface ViewsChanges {
        val scrollingStateObserver: Observer<ScrollingState>
    }

    companion object {
        fun newInstance() = CurrenciesFragment()
    }

    private val currenciesAdapter: CurrenciesAdapter by currentScope.inject { parametersOf(this) }
    override val vm: CurrenciesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.currencies_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(currencies) {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = currenciesAdapter
            setHasFixedSize(true)
            addOnScrollListener(ScrollStateChangeListener(vm.scrollingStateObserver))
        }
    }

    override fun onResume() {
        super.onResume()
        disposables += vm.itemsChanges.subscribe(currenciesAdapter::setItems)
    }

    override fun onCurrencyClicked(item: CurrencyAmountListItem) {
        vm.onCurrencyClicked(item)
    }

    override fun onBaseCurrencyAmountChanged(currency: CurrencyAmountListItem) {
        vm.onBaseCurrencyAmountChanged(currency)
    }

}
