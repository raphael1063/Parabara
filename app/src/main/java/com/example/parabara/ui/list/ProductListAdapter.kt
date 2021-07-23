package com.example.parabara.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.parabara.R
import com.example.parabara.data.entities.Row
import com.example.parabara.databinding.ItemProductBinding

class ProductListAdapter(private val viewModel: ProductListViewModel) :
    ListAdapter<Row, ProductListAdapter.ViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_product, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], viewModel)
    }

    class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(row: Row, viewModel: ProductListViewModel) {
            with(binding) {
                position = adapterPosition
                model = row
                vm = viewModel
            }
        }
    }

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Row>() {

            override fun areItemsTheSame(oldItem: Row, newItem: Row): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Row, newItem: Row): Boolean {
                return oldItem == newItem
            }

        }
    }
}