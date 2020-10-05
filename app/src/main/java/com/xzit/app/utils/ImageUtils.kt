package com.xzit.app.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.xzit.app.R
import de.hdodenhof.circleimageview.CircleImageView

open class ImageUtils {
//    open fun loadImage(context: Context, url: String, imageView: AppCompatImageView) {
//        Glide.with(context).load(url).into(imageView)
//    }
    open fun loadImage(context: Context, url: String, imageView: CircleImageView) {
        Glide.with(context).load(url).into(imageView).onLoadFailed(context.getDrawable(R.drawable.demo))
    }

    companion object {
        @JvmStatic
        fun loadImage(mContext: Context, url: String, imageView: AppCompatImageView) {
            Glide.with(mContext).load(url).into(imageView).onLoadFailed(mContext.getDrawable(R.drawable.demo))
        }
    }

}