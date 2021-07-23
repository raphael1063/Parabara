package com.example.parabara.ui.list

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter(value = ["setSwipeRefreshLayout"])
fun SwipeRefreshLayout.setLayout(viewModel: ProductListViewModel) {
    this.setOnRefreshListener {
        viewModel.refresh()
    }
}