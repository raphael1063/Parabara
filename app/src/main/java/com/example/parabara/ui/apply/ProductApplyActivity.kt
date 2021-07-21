package com.example.parabara.ui.apply

import androidx.activity.viewModels
import com.example.parabara.R
import com.example.parabara.base.BaseActivity
import com.example.parabara.databinding.ActivityProductApplyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductApplyActivity : BaseActivity<ActivityProductApplyBinding>(R.layout.activity_product_apply){

    private val viewModel: ProductApplyViewModel by viewModels()

    override fun start() {
        binding.vm = viewModel
    }

    override fun observe() {

    }
}