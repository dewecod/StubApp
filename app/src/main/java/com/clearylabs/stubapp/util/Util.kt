package com.clearylabs.stubapp.util

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class Util {
    companion object {
        /****************************************************************************************************
         **  Internet check
         ****************************************************************************************************/
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.NORMAL) // .format(DecodeFormat.PREFER_RGB_565)
        fun getRequestBuilder(context: Context): RequestBuilder<Drawable> = Glide.with(context).asDrawable().sizeMultiplier(0.2f)
    }
}