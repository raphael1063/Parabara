package com.example.parabara.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.parabara.R
import com.example.parabara.data.entities.ImageUploadResult
import com.example.parabara.databinding.ItemProductImageBinding

class ProductImageListAdapter(private val viewModel: ProductViewModel) :
    ListAdapter<ImageUploadResult, ProductImageListAdapter.ViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_product_image,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(currentList[position].url, viewModel)

    class ViewHolder(private val binding: ItemProductImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String, viewModel: ProductViewModel) {
            with(binding) {
                this.imageUrl = imageUrl
                vm = viewModel
            }
        }
    }

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<ImageUploadResult>() {

            override fun areItemsTheSame(oldItem: ImageUploadResult, newItem: ImageUploadResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ImageUploadResult, newItem: ImageUploadResult): Boolean {
                return oldItem == newItem
            }
        }
    }
}