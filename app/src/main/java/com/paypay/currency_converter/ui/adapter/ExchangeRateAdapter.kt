package com.paypay.currency_converter.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paypay.currency_converter.BR
import com.paypay.currency_converter.databinding.CurrencyListItemBinding
import com.paypay.currency_converter.model.Currency
import com.paypay.currency_converter.util.CurrencyDiffUtilCallback

class ExchangeRateAdapter:ListAdapter<Currency,ExchangeRateAdapter.RateItemViewHolder>(CurrencyDiffUtilCallback())
     {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RateItemViewHolder(CurrencyListItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RateItemViewHolder, position: Int) {
          holder.onBind(getItem(position))
    }



    inner class RateItemViewHolder(private val binding: CurrencyListItemBinding ):RecyclerView.ViewHolder(binding.root) {
     fun onBind(currency: Currency)
     {
        binding.setVariable(BR.currency,currency)
     }
    }

         override fun submitList(list: MutableList<Currency>?) {
             super.submitList(list)
         }
}