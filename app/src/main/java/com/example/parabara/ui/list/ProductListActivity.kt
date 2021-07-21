package com.example.parabara.ui.list

import androidx.activity.viewModels
import com.example.parabara.R
import com.example.parabara.base.BaseActivity
import com.example.parabara.databinding.ActivityProductListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListActivity : BaseActivity<ActivityProductListBinding>(R.layout.activity_product_list) {

    private val viewModel: ProductListViewModel by viewModels()

    private val adapter by lazy {
        ProductListAdapter(viewModel)
    }

    override fun start() {
        with(binding) {
            vm = viewModel
            rvList.adapter = adapter
        }
    }

    override fun observe() {

    }

}