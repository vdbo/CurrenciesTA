package com.vbta.currenciesta.presentation.screen.rates.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vbta.currenciesta.R
import com.vbta.currenciesta.presentation.utils.imageRes
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
        Glide.with(itemView)
            .load(item.currency.imageRes)
            .placeholder(R.drawable.ic_cur_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .circleCrop()
            .into(flag)
        code.text = item.currency.currencyCode
        name.text = item.currency.displayName
        setRate(item.rate)
    }

    fun setRate(rate: Float) {
        itemView.amount.setText(rate.toString())
    }

}