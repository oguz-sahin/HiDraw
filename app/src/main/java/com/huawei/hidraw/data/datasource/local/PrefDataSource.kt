package com.huawei.hidraw.data.datasource.local

import android.content.SharedPreferences
import com.huawei.hidraw.util.ext.defaultForType
import com.huawei.hidraw.util.ext.get
import com.huawei.hidraw.util.ext.set
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 11/15/2021.
 */

class PrefDataSource @Inject constructor(private val sharedPref: SharedPreferences) {

    var userId: String
        get() = sharedPref.get(USER_ID)
        set(value) {
            sharedPref.set(USER_ID, value)
        }


    var pushToken: String
        get() = sharedPref.get(PUSH_TOKEN)
        set(value) {
            sharedPref.set(PUSH_TOKEN, value)
        }

    fun isUserLogged(): Boolean = userId != defaultForType<String>()

    companion object {
        private const val USER_ID = "USER_ID"
        private const val PUSH_TOKEN = "PUSH_TOKEN"
    }
}
