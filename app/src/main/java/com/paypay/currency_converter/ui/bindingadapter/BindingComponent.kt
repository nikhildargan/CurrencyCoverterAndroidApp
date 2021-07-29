package com.paypay.currency_converter.ui.bindingadapter

import androidx.databinding.DataBindingComponent

class BindingComponent : DataBindingComponent {
    override fun getSpinnerBindings(): SpinnerBindings {
        return SpinnerBindings()
    }

    override fun getInverseSpinnerBindings(): InverseSpinnerBindings {
        return InverseSpinnerBindings()
    }
}