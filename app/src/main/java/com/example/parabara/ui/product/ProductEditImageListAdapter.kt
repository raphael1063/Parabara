package com.example.parabara.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.parabara.R
import com.example.parabara.data.entities.ImageUploadResult
import com.example.parabara.databinding.ItemProductEditImageBinding

class ProductEditImageListAdapter(private val viewModel: ProductViewModel) :
    ListAdapter<String, ProductEditImageListAdapter.ViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_product_edit_image,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(currentList[position], viewModel)

    class ViewHolder(private val binding: ItemProductEditImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String, viewModel: ProductViewModel) {
            with(binding) {
                position = adapterPosition
                this.imageUrl = imageUrl
                vm = viewModel
            }
        }
    }

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<String>() {

            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}