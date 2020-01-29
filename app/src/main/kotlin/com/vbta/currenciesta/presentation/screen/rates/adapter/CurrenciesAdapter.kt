package com.vbta.currenciesta.presentation.screen.rates.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.vbta.currenciesta.R
import java.text.NumberFormat

class CurrenciesAdapter(
    private val actions: CurrenciesActions,
    private val numberFormat: NumberFormat
) : RecyclerView.Adapter<CurrencyAmountViewHolder>() {

    private val diffHelper = AsyncListDiffer(
        AdapterListUpdateCallback(this),
        AsyncDifferConfig.Builder(DiffItemCallback()).build()
    )
    private val items: List<CurrencyAmountListItem> get() = diffHelper.currentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyAmountViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_currency_amount, parent, false)
        return CurrencyAmountViewHolder(view, actions, numberFormat)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CurrencyAmountViewHolder, position: Int) {
        onBindViewHolder(holder, position, emptyList())
    }

    override fun onBindViewHolder(holder: CurrencyAmountViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            holder.bind(items[position])
            return
        }
        (payloads[0] as? Payload<*>)?.let {
            when (it) {
                is Payload.Amount -> holder.updateAmount(it.value)
                else -> holder.bind(items[position])
            }
        }
    }

    fun setItems(items: List<CurrencyAmountListItem>) {
        diffHelper.submitList(items)
    }

    class DiffItemCallback : DiffUtil.ItemCallback<CurrencyAmountListItem>() {

        override fun areItemsTheSame(oldItem: CurrencyAmountListItem, newItem: CurrencyAmountListItem) =
            oldItem.javaClass == newItem.javaClass && oldItem.currency.currencyCode == newItem.currency.currencyCode

        override fun areContentsTheSame(oldItem: CurrencyAmountListItem, newItem: CurrencyAmountListItem) =
            oldItem == newItem

        override fun getChangePayload(oldItem: CurrencyAmountListItem, newItem: CurrencyAmountListItem): Any? {
            return when {
                newItem.isBase != oldItem.isBase -> Payload.Base(newItem.isBase)
                newItem.amount != oldItem.amount -> Payload.Amount(newItem.amount)
                else -> Payload.None
            }
        }

    }
}

private sealed class Payload<T>(open val value: T) {

    abstract val key: String

    data class Base(
        override val value: Boolean,
        override val key: String = "base"
    ) : Payload<Boolean>(value)

    data class Amount(
        override val value: Number,
        override val key: String = "amount"
    ) : Payload<Number>(value)

    object None : Payload<Unit>(Unit) {
        override val key: String = ""
    }

}