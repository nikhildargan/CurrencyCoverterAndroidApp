package com.paypay.currency_converter.ui.bindingadapter

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.paypay.currency_converter.ui.extensions.SpinnerExtensions
import com.paypay.currency_converter.ui.extensions.SpinnerExtensions.setSpinnerEntries
import com.paypay.currency_converter.ui.extensions.SpinnerExtensions.setSpinnerItemSelectedListener
import com.paypay.currency_converter.ui.extensions.SpinnerExtensions.setSpinnerValue

class SpinnerBindings {

    @BindingAdapter("entries")
    fun<T> setEntries( spinner: Spinner,entries: List<Any>?) {

    }

    @BindingAdapter("onItemSelected")
    fun Spinner.setItemSelectedListener(itemSelectedListener: SpinnerExtensions.ItemSelectedListener?) {
        setSpinnerItemSelectedListener(itemSelectedListener)
    }

    @BindingAdapter("newValue")
    fun Spinner.setNewValue(newValue: Any?) {
        setSpinnerValue(newValue)
    }
}