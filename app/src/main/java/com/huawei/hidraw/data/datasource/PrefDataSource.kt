package com.huawei.hidraw.data.datasource

import android.content.SharedPreferences
import com.huawei.hidraw.data.model.UserModel
import com.huawei.hidraw.util.ext.get
import com.huawei.hidraw.util.ext.set
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 11/15/2021.
 */


class PrefDataSource @Inject constructor(private val sharedPref: SharedPreferences) {

    var userInfo: UserModel?
        get() = sharedPref.get<UserModel>(USER_INFO_KEY)
        set(value) {
            sharedPref.set<UserModel?>(USER_INFO_KEY, value)
        }


    fun isUserLogged(): Boolean = userInfo != null


    companion object {
        private const val USER_INFO_KEY = "TOKEN_KEY"
    }

}