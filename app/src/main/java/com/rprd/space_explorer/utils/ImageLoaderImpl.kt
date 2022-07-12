package com.rprd.space_explorer.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

class ImageLoaderImpl(val context: Context) : ImageLoader {
    override fun loadImage(imageUrl: String?, targetSource: ImageView, @DrawableRes defaultImage: Int) {
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(context)
                    .load(convertUrl(imageUrl))
                    .into(targetSource)
        } else {
            targetSource.setImageResource(defaultImage)
        }
    }

    override fun loadImage(
            imageUrl: String?,
            resizeX: Int,
            resizeY: Int,
            targetSource: ImageView,
            defaultImage: Int
    ) {
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(context)
                    .load(convertUrl(imageUrl))
                    .override(resizeX, resizeY)
                    .into(targetSource)
        } else {
            targetSource.setImageResource(defaultImage)
        }
    }

    override fun loadImage(bitmap: Bitmap?, targetSource: ImageView, @DrawableRes defaultImage: Int) {
        if (bitmap != null) {
            Glide.with(context)
                    .load(bitmap)
                    .into(targetSource)
        } else {
            targetSource.setImageResource(defaultImage)
        }
    }

    private fun convertUrl(url: String): String {
        return if (url.contains("https")) {
            url
        } else {
            url.replace("http", "https")
        }
    }
}