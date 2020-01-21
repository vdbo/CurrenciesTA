package com.vbta.currenciesta.presentation.screen.rates.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.vbta.currenciesta.R

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
        onBindViewHolder(holder, position, emptyList())
    }

    override fun onBindViewHolder(holder: CurrencyRateViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            holder.bind(items[position])
            return
        }
        (payloads[0] as? Payload<*>)?.let {
            when (it) {
                is Payload.Rate -> holder.setAmount(it.value)
                else -> holder.bind(items[position])
            }
        }
    }

    fun setItems(items: List<CurrencyRateListItem>) {
        diffHelper.submitList(items)
    }

    class DiffItemCallback : DiffUtil.ItemCallback<CurrencyRateListItem>() {

        override fun areItemsTheSame(oldItem: CurrencyRateListItem, newItem: CurrencyRateListItem) =
            oldItem.javaClass == newItem.javaClass && oldItem.currency.currencyCode == newItem.currency.currencyCode

        override fun areContentsTheSame(oldItem: CurrencyRateListItem, newItem: CurrencyRateListItem) =
            oldItem == newItem

        override fun getChangePayload(oldItem: CurrencyRateListItem, newItem: CurrencyRateListItem): Any? {
            return if (newItem.amount != oldItem.amount) {
                Payload.Rate(newItem.amount)
            } else {
                Payload.None
            }
        }

    }
}

private sealed class Payload<T>(open val value: T) {

    abstract val key: String

    data class Rate(
        override val value: Double,
        override val key: String = "rate"
    ) : Payload<Double>(value)

    object None : Payload<Unit>(Unit) {
        override val key: String = ""
    }

}