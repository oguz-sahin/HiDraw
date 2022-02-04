package com.huawei.hidraw.util.ext

import android.widget.EditText

fun EditText.getContent(): String {
    return text.getContent()
}

fun EditText.getIntContent(): Int {
    return try {
        text?.toString()?.trim()?.toInt() ?: 0
    } catch (e: Exception) {
        -1
    }
}
