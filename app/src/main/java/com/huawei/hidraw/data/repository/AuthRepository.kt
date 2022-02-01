package com.huawei.hidraw.data.repository

import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.model.PushTokenBodyModel
import com.huawei.hidraw.data.model.UserModel

/**
 * Created by Oguz Sahin on 11/15/2021.
 */
interface AuthRepository {
    suspend fun register(userModel: UserModel): ResultWrapper<UserModel>
    fun isUserLogged(): Boolean
    fun saveUserId(userId: String)
    fun removeUser()
    fun getUserId(): String
    suspend fun sendPushTokenToServer(pushTokenBodyModel: PushTokenBodyModel): ResultWrapper<PushTokenBodyModel>
}
