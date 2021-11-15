package com.huawei.hidraw.data

import android.content.SharedPreferences
import com.huawei.hidraw.util.ext.defaultForType
import com.huawei.hidraw.util.ext.get
import com.huawei.hidraw.util.ext.set
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 11/15/2021.
 */


class PrefDataSource @Inject constructor(private val sharedPref: SharedPreferences) {

    var token
        get() = sharedPref.get<String>(TOKEN_KEY)
        set(value) {
            sharedPref.set<String>(TOKEN_KEY, value)
        }


    fun isUserLogged(): Boolean = token != defaultForType<String>()


    companion object {
        private const val TOKEN_KEY = "TOKEN_KEY"
    }

}