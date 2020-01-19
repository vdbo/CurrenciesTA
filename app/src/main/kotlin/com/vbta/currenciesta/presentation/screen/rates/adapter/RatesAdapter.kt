package com.vbta.currenciesta.presentation.screen.rates.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.vbta.currenciesta.R
import kotlinx.android.synthetic.main.item_currency_rate.view.*

class RatesAdapter(private val actions: RatesActions) : RecyclerView.Adapter<CurrencyRateViewHolder>() {

    private val diffHelper = AsyncListDiffer(
        AdapterListUpdateCallback(this),
        AsyncDifferConfig.Builder(DiffItemCallback()).build()
    )
    private val items: List<CurrencyRateListItem> get() = diffHelper.currentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_currency_rate, parent, false)
        return CurrencyRateViewHolder(view, actions)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CurrencyRateViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(items: List<CurrencyRateListItem>) {
        diffHelper.submitList(items)
    }

    class DiffItemCallback : DiffUtil.ItemCallback<CurrencyRateListItem>() {

        override fun areItemsTheSame(oldItem: CurrencyRateListItem, newItem: CurrencyRateListItem) =
            oldItem.javaClass == newItem.javaClass && oldItem.currencyAbbreviation == newItem.currencyAbbreviation

        override fun areContentsTheSame(oldItem: CurrencyRateListItem, newItem: CurrencyRateListItem) =
            oldItem == newItem

    }

}