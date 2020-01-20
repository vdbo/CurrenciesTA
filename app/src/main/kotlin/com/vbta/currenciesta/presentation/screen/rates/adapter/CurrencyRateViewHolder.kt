package com.vbta.currenciesta.presentation.screen.rates.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vbta.currenciesta.presentation.utils.getImage
import kotlinx.android.synthetic.main.item_currency_rate.view.*
import java.util.*

class CurrencyRateViewHolder(itemView: View, actions: RatesActions) : RecyclerView.ViewHolder(itemView) {

    private lateinit var item: CurrencyRateListItem

    init {
        itemView.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) actions.onRateClicked(item)
        }
    }

    fun bind(item: CurrencyRateListItem) = with(itemView) {
        this@CurrencyRateViewHolder.item = item
        flag.setImageDrawable(Currency.getInstance(item.currencyAbbreviation).getImage(itemView.context))
        abbreviation.text = item.currencyAbbreviation
        name.text = item.currencyName
        setRate(item.rate)
    }

    fun setRate(rate: Float) {
        itemView.amount.setText(rate.toString())
    }

}