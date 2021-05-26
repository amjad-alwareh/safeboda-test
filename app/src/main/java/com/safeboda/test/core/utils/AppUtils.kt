package com.safeboda.test.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun String?.text() = this ?: "-"

fun Int?.number() = this ?: 0

fun ImageView.load(url: String?) {

    if (url.isNullOrEmpty()) return

    val context = this.context

    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .into(this)
}