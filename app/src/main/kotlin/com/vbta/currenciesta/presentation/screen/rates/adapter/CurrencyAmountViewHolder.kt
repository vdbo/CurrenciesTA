package com.vbta.currenciesta.presentation.screen.rates.adapter

import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vbta.currenciesta.R
import com.vbta.currenciesta.presentation.utils.imageRes
import kotlinx.android.synthetic.main.item_currency_amount.view.*
import java.text.NumberFormat

class CurrencyAmountViewHolder(
    itemView: View,
    private val actions: CurrenciesActions,
    private val numberFormat: NumberFormat
) : RecyclerView.ViewHolder(itemView) {

    private lateinit var item: CurrencyAmountListItem
    private var ignoreTextChanges: Boolean = true

    init {
        itemView.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                it.requestFocus()
                actions.onCurrencyClicked(item)
            }
        }
        itemView.amount.doAfterTextChanged {
            if (!item.isBase || ignoreTextChanges) return@doAfterTextChanged
            actions.onBaseCurrencyAmountChanged(item.copy(amount = numberFormat.parse(it.toString())))
        }
    }

    fun bind(newItem: CurrencyAmountListItem) {
        when {
            !::item.isInitialized -> setFlag(newItem)
            item.currency.currencyCode != newItem.currency.currencyCode -> setFlag(newItem)
        }
        item = newItem
        if (item.isBase) bindAsBaseCurrency() else bindAsOtherCurrency()
    }

    fun updateAmount(amount: Number) {
        item = item.copy(amount = amount)
        if (!item.isBase) itemView.amount.setText(numberFormat.format(item.amount.toDouble()))
    }

    private fun setFlag(newItem: CurrencyAmountListItem) {
        Glide.with(itemView)
            .load(newItem.currency.imageRes)
            .placeholder(R.drawable.ic_cur_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .circleCrop()
            .into(itemView.flag)
    }

    private fun bindAsBaseCurrency() {
        itemView.code.text = item.currency.currencyCode
        itemView.name.text = item.currency.displayName
        itemView.amount.isEnabled = true
        ignoreTextChanges = true
        itemView.amount.setText(numberFormat.format(item.amount.toDouble()))
        ignoreTextChanges = false
    }

    private fun bindAsOtherCurrency() {
        itemView.code.text = item.currency.currencyCode
        itemView.name.text = item.currency.displayName
        itemView.amount.isEnabled = false
        itemView.amount.setText(numberFormat.format(item.amount.toDouble()))
    }

}