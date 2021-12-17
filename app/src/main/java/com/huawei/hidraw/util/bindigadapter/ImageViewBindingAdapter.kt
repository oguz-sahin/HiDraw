package com.huawei.hidraw.util.bindigadapter

import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.databinding.BindingAdapter
import com.huawei.hidraw.util.ext.load

/**
 * Created by Oguz Sahin on 12/2/2021.
 */

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    if (!url.isNullOrEmpty()) {
        load(url)
    }
}


@BindingAdapter("imageSrc")
fun ImageView.loadImage(resId: Int) {
    setImageResource(resId)
}