/*
 * Copyright 2022. Explore in HMS. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
