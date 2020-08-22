package com.xzit.app.utils

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

open class ImageUtils {
    open fun loadImage(context: Context, url: String, imageView: AppCompatImageView) {
        Glide.with(context).load(url).into(imageView)
    }

}