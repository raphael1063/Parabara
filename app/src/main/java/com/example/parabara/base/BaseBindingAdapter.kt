package com.example.parabara.base

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.example.parabara.R

@BindingAdapter(value = ["bind:setProductImage"])
fun ImageView.setImage(imageUrl: String?) {
    Glide.with(context)
        .load(imageUrl)
        .centerCrop()
        .placeholder(R.drawable.ic_baseline_image_24)
        .error(R.drawable.ic_baseline_image_24)
        .fallback(R.drawable.ic_baseline_image_24)
        .into(this)
}