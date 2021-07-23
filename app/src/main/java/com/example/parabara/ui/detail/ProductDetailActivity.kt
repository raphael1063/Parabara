/*
상품 상세
 */
package com.example.parabara.ui.detail

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.parabara.R
import com.example.parabara.base.BaseActivity
import com.example.parabara.base.PRODUCT_ID
import com.example.parabara.base.PRODUCT_INFO
import com.example.parabara.databinding.ActivityProductDetailBinding
import com.example.parabara.ui.alert.AlertDialogFragment
import com.example.parabara.ui.product.ProductActivity
import com.example.parabara.util.CirclePagerIndicatorDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity :
    BaseActivity<ActivityProductDetailBinding>(R.layout.activity_product_detail), AlertDialogFragment.AlertDialogResultListener {

    private val viewModel: ProductDetailViewModel by viewModels()

    private val adapter by lazy {
        ProductDetailImageListAdapter()
    }

    private val productEditorLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                viewModel.refresh()
            }
        }

    override fun start() {
        with(binding) {
            vm = viewModel
            rvDetailImageList.adapter = adapter
            rvDetailImageList.addItemDecoration(CirclePagerIndicatorDecoration())
            PagerSnapHelper().attachToRecyclerView(rvDetailImageList)
        }
        viewModel.loadData(intent.getLongExtra(PRODUCT_ID, -1))
    }

    override fun observe() {
        with(viewModel) {
            productImageList.observe(this@ProductDetailActivity, {
                adapter.submitList(it)
            })
            actionEditButtonClicked.observe(this@ProductDetailActivity, { event ->
                event.getContentIfNotHandled()?.let { productInfo ->
                    productEditorLauncher.launch(Intent(this@ProductDetailActivity, ProductActivity::class.java).apply {
                        putExtra(PRODUCT_INFO, productInfo)
                    })
                }
            })
            actionRemoveButtonClicked.observe(this@ProductDetailActivity, { event ->
                event.getContentIfNotHandled()?.let {
                    AlertDialogFragment().apply {
                        setListener(this@ProductDetailActivity )
                    }.show(supportFragmentManager, null)
                }
            })
            finishActivity.observe(this@ProductDetailActivity, { event ->
                event.getContentIfNotHandled()?.let {
                    setResult(it)
                    finish()
                }
            })
        }
    }

    override fun onBackPressed() {
        setResult(RESULT_CANCELED)
        super.onBackPressed()
    }

    override fun onDeleteClicked() {
        viewModel.onDeleteButtonClicked()
    }
}