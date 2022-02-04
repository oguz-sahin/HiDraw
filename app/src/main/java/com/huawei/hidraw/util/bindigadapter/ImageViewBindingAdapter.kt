package com.huawei.hidraw.util.bindigadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.huawei.hidraw.util.ext.load

/**
 * Created by Oguz Sahin on 12/2/2021.
 */

@BindingAdapter("imageSrc")
fun ImageView.loadImage(url: Any?) {
    url?.let {
        load(it)
    }
}

