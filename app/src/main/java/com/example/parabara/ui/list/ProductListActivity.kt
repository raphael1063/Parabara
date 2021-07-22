package com.example.parabara.ui.list

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.parabara.R
import com.example.parabara.base.BaseActivity
import com.example.parabara.databinding.ActivityProductListBinding
import com.example.parabara.ext.openActivity
import com.example.parabara.ext.toast
import com.example.parabara.ui.detail.ProductDetailActivity
import com.example.parabara.ui.product.ProductActivity
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody

@AndroidEntryPoint
class ProductListActivity :
    BaseActivity<ActivityProductListBinding>(R.layout.activity_product_list) {

    private val viewModel: ProductListViewModel by viewModels()

    private val adapter by lazy {
        ProductListAdapter(viewModel)
    }

    private val productDetailLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                viewModel.refresh()
            }
        }

    override fun start() {
        with(binding) {
            vm = viewModel
            rvList.adapter = adapter
        }
    }

    override fun observe() {
        with(viewModel) {
            list.observe(this@ProductListActivity, { list ->
                adapter.submitList(list)
            })
            actionProductItemClicked.observe(this@ProductListActivity, { event ->
                event.getContentIfNotHandled()?.let { id ->
                    productDetailLauncher.launch(Intent(this@ProductListActivity, ProductDetailActivity::class.java).apply {
                        putExtra("ProductId", id)
                    })
                }
            })
            actionApplyButtonClicked.observe(this@ProductListActivity, { event ->
                event.getContentIfNotHandled()?.let {
                    productDetailLauncher.launch(Intent(this@ProductListActivity, ProductActivity::class.java))
                }
            })
            showToastInt.observe(this@ProductListActivity, { event ->
                event.getContentIfNotHandled()?.let {
                    toast(it)
                }
            })
        }
    }

}