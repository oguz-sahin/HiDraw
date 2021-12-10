package com.huawei.hidraw.network

import com.huawei.hidraw.data.model.BaseResponseModel
import com.huawei.hidraw.data.model.UserModel
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Oguz Sahin on 11/12/2021.
 */

interface UserService {

    @POST("api/user")
    suspend fun saveUser(
        @Body userModel: UserModel
    ): BaseResponseModel<Boolean>
}