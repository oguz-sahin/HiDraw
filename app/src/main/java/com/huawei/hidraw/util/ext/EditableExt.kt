package com.huawei.hidraw.util.ext

import android.text.Editable


fun Editable?.getContent(): String {
    return this?.toString()?.trim() ?: ""
}
