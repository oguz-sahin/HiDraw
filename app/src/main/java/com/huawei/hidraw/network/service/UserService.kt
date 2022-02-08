package com.huawei.hidraw.network.service

import com.huawei.hidraw.data.model.BaseResponseModel
import com.huawei.hidraw.data.model.PushTokenBodyModel
import com.huawei.hidraw.data.model.UserDetailModel
import com.huawei.hidraw.data.model.UserModel
import com.huawei.hidraw.network.NetworkUtils.HEADER_KEY
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created by Oguz Sahin on 11/12/2021.
 */

interface UserService {

    @POST(USER_REQUEST_PATH)
    suspend fun saveUser(
        @Body userModel: UserModel
    ): BaseResponseModel<UserModel>

    @GET(USER_REQUEST_PATH)
    suspend fun getUser(): BaseResponseModel<UserDetailModel>

    @POST(SAVE_PUSH_TOKEN_PATH)
    suspend fun sendPushTokenToServer(
        @Header(HEADER_KEY) userId: String,
        @Body pushTokenBodyModel: PushTokenBodyModel
    ): BaseResponseModel<PushTokenBodyModel>

    companion object {
        const val USER_REQUEST_PATH = "user"
        const val SAVE_PUSH_TOKEN_PATH = "pushToken"
    }
}
