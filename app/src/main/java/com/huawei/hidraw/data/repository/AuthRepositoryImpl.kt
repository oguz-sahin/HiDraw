package com.huawei.hidraw.data.repository

import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.datasource.AuthRemoteDataSource
import com.huawei.hidraw.data.datasource.PrefDataSource
import com.huawei.hidraw.data.model.UserModel
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 11/15/2021.
 */

class AuthRepositoryImpl @Inject constructor(
    private val prefDataSource: PrefDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun register(userModel: UserModel): ResultWrapper<Boolean> {
        return authRemoteDataSource.register(userModel)
    }

    override fun isUserLogged(): Boolean = prefDataSource.isUserLogged()

    override fun saveUser(userModel: UserModel) {
        prefDataSource.userInfo = userModel
    }

    override fun removeUser() {
        prefDataSource.userInfo = null
    }


}