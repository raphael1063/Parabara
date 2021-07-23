package com.example.parabara.ui.list

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parabara.R
import com.example.parabara.base.BaseActivity
import com.example.parabara.base.PRODUCT_ID
import com.example.parabara.databinding.ActivityProductListBinding
import com.example.parabara.ext.openActivity
import com.example.parabara.ext.toast
import com.example.parabara.ui.detail.ProductDetailActivity
import com.example.parabara.ui.product.ProductActivity
import com.example.parabara.util.EndlessRecyclerViewScrollListener
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody
import timber.log.Timber

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

    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun start() {
        with(binding) {
            vm = viewModel
            rvList.adapter = adapter
        }
        scrollListener = object : EndlessRecyclerViewScrollListener(binding.rvList.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                Timber.d("scroll -> page : $page")
                viewModel.loadMore(page)
            }
        }
        binding.rvList.addOnScrollListener(scrollListener)
    }

    override fun observe() {
        with(viewModel) {
            list.observe(this@ProductListActivity, { list ->
                adapter.submitList(list)
            })
            hideRefreshing.observe(this@ProductListActivity, {
                binding.srlList.isRefreshing = false
                scrollListener.resetState()
            })
            actionProductItemClicked.observe(this@ProductListActivity, { event ->
                event.getContentIfNotHandled()?.let { id ->
                    productDetailLauncher.launch(Intent(this@ProductListActivity, ProductDetailActivity::class.java).apply {
                        putExtra(PRODUCT_ID, id)
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