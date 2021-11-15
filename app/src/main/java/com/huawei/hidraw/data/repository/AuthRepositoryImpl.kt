package com.huawei.hidraw.data.repository

import com.huawei.hidraw.data.PrefDataSource
import com.huawei.hidraw.util.ext.defaultForType
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 11/15/2021.
 */

class AuthRepositoryImpl @Inject constructor(
    private val prefDataSource: PrefDataSource
) : AuthRepository {
    override suspend fun signIn() {
        //network
    }

    override fun isUserLogged(): Boolean = prefDataSource.isUserLogged()

    override fun saveToken(token: String) {
        prefDataSource.token = token
    }

    override fun removeToken() {
        prefDataSource.token = defaultForType()
    }
}