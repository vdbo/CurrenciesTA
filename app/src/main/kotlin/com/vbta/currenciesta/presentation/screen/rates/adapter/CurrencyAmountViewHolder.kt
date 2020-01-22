package com.vbta.currenciesta.presentation.screen.rates.adapter

import android.text.TextWatcher
import android.view.View
import androidx.core.widget.doOnTextChanged
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
    private var amountTextWatcher: TextWatcher? = null

    init {
        itemView.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) actions.onCurrencyClicked(item)
        }
    }

    fun bind(item: CurrencyAmountListItem) = with(itemView) {
        this@CurrencyAmountViewHolder.item = item
        Glide.with(itemView)
            .load(item.currency.imageRes)
            .placeholder(R.drawable.ic_cur_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .circleCrop()
            .into(flag)
        code.text = item.currency.currencyCode
        name.text = item.currency.displayName
        setAmount(item.amount)
        setAmountChangeListener(item.isBase)
    }

    fun setAmount(amount: Double) {
        itemView.amount.setText(amount.toString())
        itemView.amount.setSelection(amount.toString().length)
    }

    private fun setAmountChangeListener(isBase: Boolean) = with(itemView) {
        if (!isBase) {
            amountTextWatcher?.let { amount.removeTextChangedListener(it) }
            return@with
        }
        amountTextWatcher = amount.doOnTextChanged { text, _, _, _ ->
            actions.onBaseCurrencyAmountChanged(text.toString().toDouble())
        }
    }

}