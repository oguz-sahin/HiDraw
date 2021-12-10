package com.huawei.hidraw.util.ext

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson

/**
 * Created by Oguz Sahin on 11/15/2021.
 */


inline operator fun <reified T> SharedPreferences.set(key: String, value: T) {
    edit {
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            is Long -> putLong(key, value)
            else -> Gson().toJson(value).also { putString(key, it) }
        }
    }
}


inline operator fun <reified T> SharedPreferences.get(
    key: String
) =
    when (T::class) {
        String::class -> getString(key, defaultForType<String>()) as T
        Int::class -> getInt(key, defaultForType()) as T
        Boolean::class -> getBoolean(key, defaultForType()) as T
        Float::class -> getFloat(key, defaultForType()) as T
        Long::class -> getLong(key, defaultForType()) as T
        else -> Gson().fromJson(getString(key, defaultForType<String>()), T::class.java)
    }


inline fun <reified T> defaultForType(): T =
    when (T::class) {
        String::class -> "" as T
        Int::class -> 0 as T
        Boolean::class -> false as T
        Float::class -> 0F as T
        Long::class -> 0L as T
        else -> throw IllegalArgumentException("Default value not found for type ${T::class}")
    }

