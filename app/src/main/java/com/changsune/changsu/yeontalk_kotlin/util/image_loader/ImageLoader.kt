package com.changsune.changsu.yeontalk_kotlin.util.image_loader

import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions
import com.changsune.changsu.yeontalk_kotlin.R

fun ImageView.loadImage(uri: String?) {

    val options = RequestOptions().centerCrop()

    GlideApp.with(this.context)
            .load(uri)
            .placeholder(R.color.gainsboro)
            .apply(options)
            .into(this)
}