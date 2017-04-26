package com.droibit.hello.firebase

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView

object BindingAdapters {

    @BindingAdapter("app:srcCompat")
    @JvmStatic
    fun setImageResource(view: ImageView, drawable: Drawable) {
        view.setImageDrawable(drawable)
    }
}