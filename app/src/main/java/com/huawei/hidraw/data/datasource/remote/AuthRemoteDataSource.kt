package com.huawei.hidraw.data.datasource.remote

import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.model.PushTokenBodyModel
import com.huawei.hidraw.data.model.UserModel
import com.huawei.hidraw.network.service.UserService
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 11/16/2021.
 */
class AuthRemoteDataSource @Inject constructor(
    private val userService: UserService
) : BaseRemoteDataSource() {

    suspend fun register(userModel: UserModel): ResultWrapper<UserModel> {
        return safeApiCall { userService.saveUser(userModel) }
    }

    suspend fun sendPushTokenToServer(pushTokenBodyModel: PushTokenBodyModel): ResultWrapper<PushTokenBodyModel> {
        return safeApiCall { userService.sendPushTokenToServer(pushTokenBodyModel) }
    }
}
