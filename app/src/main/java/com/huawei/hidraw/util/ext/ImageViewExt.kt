package com.huawei.hidraw.util.ext

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by Oguz Sahin on 10/26/2021.
 */

fun ImageView.load(source: Any) {
    Glide.with(this.context).load(source).into(this)
}
