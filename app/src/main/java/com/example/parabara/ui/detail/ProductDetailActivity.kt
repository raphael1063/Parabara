package com.example.parabara.ui.detail

import androidx.activity.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.parabara.R
import com.example.parabara.base.BaseActivity
import com.example.parabara.databinding.ActivityProductDetailBinding
import com.example.parabara.util.CirclePagerIndicatorDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>(R.layout.activity_product_detail){

    private val viewModel: ProductDetailViewModel by viewModels()

    private val adapter by lazy {
        ProductDetailImageListAdapter()
    }

    override fun start() {
        with(binding) {
            vm = viewModel
            rvDetailImageList.adapter = adapter
            rvDetailImageList.addItemDecoration(CirclePagerIndicatorDecoration())
            PagerSnapHelper().attachToRecyclerView(rvDetailImageList)
        }
        viewModel.loadData(intent.getLongExtra("ProductId", -1))
    }

    override fun observe() {
        with(viewModel) {
            productImageList.observe(this@ProductDetailActivity, {
                adapter.submitList(it)
            })
        }
    }
}