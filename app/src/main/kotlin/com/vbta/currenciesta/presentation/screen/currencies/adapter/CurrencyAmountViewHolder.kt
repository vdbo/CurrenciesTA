package com.vbta.currenciesta.presentation.screen.currencies.adapter

import android.text.InputFilter
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vbta.currenciesta.R
import com.vbta.currenciesta.presentation.utils.forceRequestFocus
import com.vbta.currenciesta.presentation.utils.imageRes
import kotlinx.android.synthetic.main.item_currency_amount.view.*
import java.text.DecimalFormat

class CurrencyAmountViewHolder(
    itemView: View,
    private val glide: RequestManager,
    private val actions: CurrenciesActions,
    private val numberFormat: DecimalFormat,
    private val inputFilter: InputFilter
) : RecyclerView.ViewHolder(itemView) {

    private lateinit var item: CurrencyAmountListItem
    private var ignoreTextChanges: Boolean = true

    init {
        val clickListener = { _: View ->
            if (adapterPosition != RecyclerView.NO_POSITION) {
                itemView.forceRequestFocus()
                actions.onCurrencyClicked(item)
            }
        }
        itemView.setOnClickListener(clickListener)
        itemView.amount.setOnClickListener { if (!item.isBase) clickListener(it) }
        itemView.amount.doAfterTextChanged {
            if (!item.isBase || ignoreTextChanges) return@doAfterTextChanged

            val value = if (it.isNullOrEmpty()) 0 else numberFormat.parse(it.toString())
            actions.onBaseCurrencyAmountChanged(item.copy(amount = value))
        }
        itemView.amount.filters = arrayOf(inputFilter)
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
        glide.load(newItem.currency.imageRes)
            .placeholder(R.drawable.ic_cur_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .circleCrop()
            .into(itemView.flag)
    }

    private fun bindAsBaseCurrency() {
        itemView.code.text = item.currency.currencyCode
        itemView.name.text = item.currency.displayName
        itemView.amount.isFocusable = true
        itemView.amount.isFocusableInTouchMode = true
        ignoreTextChanges = true
        itemView.amount.setText(numberFormat.format(item.amount.toDouble()))
        ignoreTextChanges = false
    }

    private fun bindAsOtherCurrency() {
        itemView.code.text = item.currency.currencyCode
        itemView.name.text = item.currency.displayName
        itemView.amount.isFocusable = false
        itemView.amount.isFocusableInTouchMode = false
        itemView.amount.setText(numberFormat.format(item.amount.toDouble()))
    }

}