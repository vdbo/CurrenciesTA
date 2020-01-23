package com.vbta.currenciesta.presentation.screen.rates.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vbta.currenciesta.R
import com.vbta.currenciesta.presentation.utils.imageRes
import kotlinx.android.synthetic.main.item_currency_amount.view.*

class CurrencyAmountViewHolder(
    itemView: View,
    private val actions: CurrenciesActions
) : RecyclerView.ViewHolder(itemView) {

    private lateinit var item: CurrencyAmountListItem

    init {
        itemView.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                it.requestFocus()
                actions.onCurrencyClicked(item)
            }
        }
    }

    fun bind(newItem: CurrencyAmountListItem) {
        when {
            !::item.isInitialized -> setFlag(newItem)
            item.currency.currencyCode != newItem.currency.currencyCode -> setFlag(newItem)
        }
        item = newItem
        itemView.code.text = item.currency.currencyCode
        itemView.name.text = item.currency.displayName
        setBase(item.isBase)
        setAmount(item.amount)
    }

    fun setBase(isBase: Boolean) {
        item = item.copy(isBase = isBase)
        itemView.amount.isEnabled = item.isBase
    }

    fun setAmount(amount: Number) {
        item = item.copy(amount = amount)
        itemView.amount.setText(amount.toString())
        itemView.amount.setSelection(amount.toString().length)
    }

    private fun setFlag(newItem: CurrencyAmountListItem) {
        Glide.with(itemView)
            .load(newItem.currency.imageRes)
            .placeholder(R.drawable.ic_cur_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .circleCrop()
            .into(itemView.flag)
    }

}