package com.huawei.hidraw.data.repository

import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.datasource.local.PrefDataSource
import com.huawei.hidraw.data.datasource.remote.AuthRemoteDataSource
import com.huawei.hidraw.data.model.UserModel
import com.huawei.hidraw.util.ext.defaultForType
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 11/15/2021.
 */

class AuthRepositoryImpl @Inject constructor(
    private val prefDataSource: PrefDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun register(userModel: UserModel): ResultWrapper<UserModel> {
        return authRemoteDataSource.register(userModel)
    }

    override fun isUserLogged(): Boolean = prefDataSource.isUserLogged()

    override fun saveUserId(userId: String) {
        prefDataSource.userId = userId
    }

    override fun removeUser() {
        prefDataSource.userId = defaultForType()
    }
}
