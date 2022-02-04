package com.huawei.hidraw.util.ext

import android.annotation.SuppressLint
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

/**
 * Created by Oguz Sahin on 12/3/2021.
 */

@SuppressLint("SimpleDateFormat")
fun Long.convertTimeStampWithFormat(formatPattern: String = "dd.MM.yyyy"): String? {
    return try {
        val date = Date(this)
        val format = SimpleDateFormat(formatPattern)
        format.format(date)
    } catch (e: Exception) {
        null
    }
}

fun Long.getDifferenceDayByNow(): Int {
    val now = System.currentTimeMillis()
    val difference = abs(this - now)
    return TimeUnit.MILLISECONDS.toDays(difference).toInt()
}

fun Long.isPassed(): Boolean {
    val now = Timestamp(System.currentTimeMillis())
    return now.after(Date(this))
}
