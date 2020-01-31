package com.vbta.currenciesta.presentation.screen.currencies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.vbta.currenciesta.R
import com.vbta.currenciesta.presentation.screen.base.BaseFragment
import com.vbta.currenciesta.presentation.screen.base.DataState
import com.vbta.currenciesta.presentation.screen.currencies.adapter.CurrenciesActions
import com.vbta.currenciesta.presentation.screen.currencies.adapter.CurrenciesAdapter
import com.vbta.currenciesta.presentation.screen.currencies.adapter.CurrencyAmountListItem
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
        private const val SKIP_BASE_CURRENCY_CHANGE_MS = 500

        fun newInstance() = CurrenciesFragment()
    }

    private val currenciesAdapter: CurrenciesAdapter by currentScope.inject { parametersOf(Glide.with(this), this) }
    private var lastTimeCurrencyChanged: Long = 0
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
        disposables += vm.itemsChanges.subscribe {
            loading.isVisible = it is DataState.Loading
            currencies.isVisible = it is DataState.Loaded
            failed.isVisible = it is DataState.Failed
            if (it is DataState.Loaded) {
                currenciesAdapter.setItems(it.data)
            }
        }
    }

    override fun onCurrencyClicked(item: CurrencyAmountListItem) {
        if (System.currentTimeMillis() - lastTimeCurrencyChanged < SKIP_BASE_CURRENCY_CHANGE_MS) return

        lastTimeCurrencyChanged = System.currentTimeMillis()
        vm.baseCurrencyChanges.onNext(item.copy(isBase = true))
    }

    override fun onBaseCurrencyAmountChanged(currency: CurrencyAmountListItem) {
        vm.baseCurrencyChanges.onNext(currency)
    }

}
