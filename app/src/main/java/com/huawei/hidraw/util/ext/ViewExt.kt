package com.huawei.hidraw.util.ext

import android.view.View
import android.widget.EditText
import java.lang.Exception

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

fun EditText.getIntContent(): Int {
    return try {
        text?.toString()?.trim()?.toInt() ?: 0
    }catch (e: Exception){
        -1
    }

}



