package com.apiumhub.vyou.ui.extension

import android.widget.ImageView
import com.apiumhub.vyou.R
import com.squareup.picasso.Picasso

fun ImageView.load(url: String?) {
    Picasso.get().load(url).placeholder(R.drawable.image_error_placeholder).into(this)
}