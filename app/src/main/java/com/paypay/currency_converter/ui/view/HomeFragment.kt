package com.paypay.currency_converter.ui.view

import GridSpacingItemDecoration
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.paypay.currency_converter.BR
import com.paypay.currency_converter.R
import com.paypay.currency_converter.databinding.FragmentHomeBinding
import com.paypay.currency_converter.ui.adapter.ExchangeRateAdapter
import com.paypay.currency_converter.ui.base.BaseFragment
import com.paypay.currency_converter.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped

@AndroidEntryPoint
@FragmentScoped
class HomeFragment: BaseFragment<FragmentHomeBinding,HomeViewModel>() {
    override val layoutId= R.layout.fragment_home
    val viewModel:HomeViewModel by viewModels()

    private val exchangeRateAdapter by lazy {
        ExchangeRateAdapter()
    }
    override fun init(savedInstanceState: Bundle?) {
        viewModel.fetchCurrencies()
      viewModel.currencyListData.observe(this){currencyList->
        exchangeRateAdapter.submitList(currencyList)
      }
     viewModel.error.observe(this)
     {
     Toast.makeText(this.context,it?:"",Toast.LENGTH_LONG).show()
     }
    }

    override fun initView(savedInstanceState: Bundle?) {
      binding.lifecycleOwner=this
      binding.setVariable(BR.model,viewModel)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val space=30
        val spanCount=3
        val recyclerView=binding.exchangeRateList
        recyclerView.layoutManager=GridLayoutManager(this.context,spanCount)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount,space,false))
        recyclerView.adapter=exchangeRateAdapter
    }
}