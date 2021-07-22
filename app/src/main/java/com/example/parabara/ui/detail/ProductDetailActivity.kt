package com.example.parabara.ui.detail

import androidx.activity.viewModels
import com.example.parabara.R
import com.example.parabara.base.BaseActivity
import com.example.parabara.databinding.ActivityProductDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>(R.layout.activity_product_detail){

    private val viewModel: ProductDetailViewModel by viewModels()

    override fun start() {
        binding.vm = viewModel
    }

    override fun observe() {
    }
}