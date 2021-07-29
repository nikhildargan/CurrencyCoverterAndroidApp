package com.paypay.currency_converter.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.paypay.currency_converter.R
import com.paypay.currency_converter.databinding.FragmentSplashBinding
import com.paypay.currency_converter.model.Resource
import com.paypay.currency_converter.ui.base.BaseFragment
import com.paypay.currency_converter.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
@FragmentScoped
class SplashFragment: BaseFragment<FragmentSplashBinding,SplashViewModel>() {

    override val layoutId: Int = R.layout.fragment_splash


   /* override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }*/

    /*override fun onStart() {
        super.onStart()
        lifecycleScope.launch(Dispatchers.IO) {
            *//**
             * Small delay so the user can actually see the splash screen
             * for a moment as feedback of an attempt to retrieve data.
             *//*
            delay(250)
            when (val result = viewModel.fetchCurrencies()) {
                is Resource.Success -> {
                    withContext(Dispatchers.Main) {
                        //findNavController().navigate(R.id.action_splashFragment_to_watchlistFragment)
                    }
                }
                is Resource.Error -> {
                    result.message?.let {
                        Log.e(this.javaClass.simpleName, it)
                    }
                    withContext(Dispatchers.Main) {
                        //findNavController().navigate(R.id.action_splashFragment_to_errorFragment)
                    }
                }
            }
        }
    }*/

    override fun init(savedInstanceState: Bundle?) {
    }

    override fun initView(savedInstanceState: Bundle?) {
        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
    }
}
