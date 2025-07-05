package com.navaplayer.util

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.navaplayer.R

object BindingAdapters {

    // ⬜️ برای عکس‌های مربعی مثل آهنگ‌ها
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String?) {
        Log.d("🟨NAVADEBUG", "🖼 Loading image: $url")
        if (!url.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(url)
                .into(view)
        }
    }

    // 🟢 برای عکس‌های دایره‌ای مثل Artist ها
    @JvmStatic
    @BindingAdapter("circleImageUrl")
    fun loadCircleImage(view: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(view)
        }
    }
}
