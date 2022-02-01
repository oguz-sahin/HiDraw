package com.huawei.hidraw.data.datasource.local

import android.content.SharedPreferences
import com.huawei.hidraw.util.ext.defaultForType
import com.huawei.hidraw.util.ext.getValue
import com.huawei.hidraw.util.ext.setValue
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 11/15/2021.
 */

class PrefDataSource @Inject constructor(private val sharedPref: SharedPreferences) {

    var userId: String
        get() = sharedPref.getValue(USER_ID)
        set(value) {
            sharedPref.setValue(USER_ID, value)
        }


    fun isUserLogged(): Boolean = userId != defaultForType<String>()


    companion object {
        private const val USER_ID = "USER_ID"
    }
}
