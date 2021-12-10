package com.huawei.hidraw.data.datasource

import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.model.UserModel
import com.huawei.hidraw.di.IoDispatcher
import com.huawei.hidraw.network.UserService
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 11/16/2021.
 */
class AuthRemoteDataSource @Inject constructor(
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    private val userService: UserService
) : BaseRemoteDataSource(ioDispatcher) {

    suspend fun register(userModel: UserModel): ResultWrapper<Boolean> {
        return safeApiCall { userService.saveUser(userModel) }
    }
}