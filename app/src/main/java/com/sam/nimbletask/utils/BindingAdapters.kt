package com.sam.nimbletask.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String) {
    Glide.with(view.getContext())
        .load(imageUrl + "l")
        .into(view)

}
