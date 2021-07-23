package com.example.parabara.ui.list

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.parabara.util.EndlessRecyclerViewScrollListener

@BindingAdapter(value = ["setSwipeRefreshLayout"])
fun SwipeRefreshLayout.setLayout(viewModel: ProductListViewModel) {
    this.setOnRefreshListener {
        viewModel.refresh()
    }
}

@BindingAdapter(value = ["setScrollListener"])
fun RecyclerView.setScrollListener(viewModel: ProductListViewModel) {

}
