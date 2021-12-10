package com.huawei.hidraw.util.ext

import android.view.View
import android.widget.EditText

/**
 * Created by Oguz Sahin on 10/26/2021.
 */


fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun EditText.getContent(): String {
    return text?.toString()?.trim() ?: ""
}


