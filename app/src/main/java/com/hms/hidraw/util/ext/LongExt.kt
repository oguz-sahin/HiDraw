/*
 * Copyright 2022. Explore in HMS. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hms.hidraw.util.ext

import android.annotation.SuppressLint
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

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
