package com.paypay.currency_converter.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController

abstract class BaseFragment<T : ViewDataBinding, V : ViewModel> : Fragment() {

    abstract val layoutId: Int
    protected lateinit var binding: T
    protected val navController
        get() = findNavController()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        //binding.setVariable(BR.model, viewModel)
        binding.lifecycleOwner = this

        initView(savedInstanceState)

        return binding.root
    }

    abstract fun init(savedInstanceState: Bundle?)

    abstract fun initView(savedInstanceState: Bundle?)




}
