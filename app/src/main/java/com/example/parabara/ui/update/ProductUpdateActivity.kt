package com.example.parabara.ui.update

import androidx.activity.viewModels
import com.example.parabara.R
import com.example.parabara.base.BaseActivity
import com.example.parabara.databinding.ActivityProductUpdateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductUpdateActivity : BaseActivity<ActivityProductUpdateBinding>(R.layout.activity_product_update){

    private val viewModel: ProductUpdateViewModel by viewModels()

    override fun start() {
        binding.vm = viewModel
    }

    override fun observe() {

    }
}