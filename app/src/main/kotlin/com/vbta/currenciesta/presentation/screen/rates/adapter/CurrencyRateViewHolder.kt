package com.vbta.currenciesta.presentation.screen.rates.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_currency_rate.view.*

class CurrencyRateViewHolder(itemView: View, actions: RatesActions) : RecyclerView.ViewHolder(itemView) {

    private lateinit var item: CurrencyRateListItem

    init {
        itemView.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) actions.onRateClicked(item)
        }
    }

    fun bind(item: CurrencyRateListItem) = with(itemView) {
        this@CurrencyRateViewHolder.item = item
        abbreviation.text = item.currencyAbbreviation
        name.text = item.currencyName
        amount.setText(item.rate.toString())
    }

}