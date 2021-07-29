package com.paypay.currency_converter.util

import androidx.recyclerview.widget.DiffUtil
import com.paypay.currency_converter.model.Currency


class CurrencyDiffUtilCallback : DiffUtil.ItemCallback<Currency>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem.currencyCode == newItem.currencyCode
    }

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem.currencyCode == newItem.currencyCode &&
                oldItem.exchangeRate == newItem.exchangeRate
    }
}