package com.example.parabara.base

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.parabara.R
import java.text.DecimalFormat

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

@BindingAdapter(value = ["bind:setRoundedProductImage"])
fun ImageView.setRoundedImage(imageUrl: String?) {
    Glide.with(context)
        .load(imageUrl)
        .transform(CenterCrop(), RoundedCorners(8))
        .placeholder(R.drawable.ic_baseline_image_24)
        .error(R.drawable.ic_baseline_image_24)
        .fallback(R.drawable.ic_baseline_image_24)
        .into(this)
}

@SuppressLint("SetTextI18n")
@BindingAdapter(value = ["bind:setDecimalFormat"])
fun TextView.setFormat(price: Int) {
    val priceFormat = DecimalFormat("###,###")
    text = "${priceFormat.format(price.toLong())}원"
}