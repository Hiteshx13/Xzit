package com.xzit.app.utils

import android.content.Context
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.xzit.app.R
import de.hdodenhof.circleimageview.CircleImageView

open class ImageUtils {
    //    open fun loadImage(context: Context, url: String, imageView: AppCompatImageView) {
//        Glide.with(context).load(url).into(imageView)
//    }
    open fun loadImage(context: Context, url: String, imageView: CircleImageView) {
        Log.d("#ImageLoading ", "" + url)
//        Glide.with(context)
//                .load(url)
//                .into(imageView)
//                .waitForLayout()
//                .apply(RequestOptions.skipMemoryCacheOf(true))
//                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
//                .onLoadFailed(context.getDrawable(R.drawable.app_icon))
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.skipMemoryCacheOf(false))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(imageView).onLoadFailed(context.getDrawable(R.drawable.demo));

    }

    open fun loadImage(context: Context, url: String, imageView: AppCompatImageView) {
        Log.d("#ImageLoading ", "" + url)
//        Glide.with(context)
//                .load(url)
//                .into(imageView)
//                .waitForLayout()
//                .apply(RequestOptions.skipMemoryCacheOf(true))
//                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
//                .onLoadFailed(context.getDrawable(R.drawable.app_icon))
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(imageView).onLoadFailed(context.getDrawable(R.drawable.app_icon));

    }

//    companion object {
//        @JvmStatic
//        fun loadImage(mContext: Context, url: String, imageView: AppCompatImageView) {
//            Glide.with(mContext).load(url).into(imageView).onLoadFailed(mContext.getDrawable(R.drawable.demo))
//        }
//    }

}