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

package com.huawei.hidraw.data.repository

import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.datasource.local.PrefDataSource
import com.huawei.hidraw.data.datasource.remote.AuthRemoteDataSource
import com.huawei.hidraw.data.model.PushTokenBodyModel
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

    override fun getUserId(): String = prefDataSource.userId

    override suspend fun sendPushTokenToServer(pushTokenBodyModel: PushTokenBodyModel): ResultWrapper<PushTokenBodyModel> {
        return authRemoteDataSource.sendPushTokenToServer(prefDataSource.userId, pushTokenBodyModel)
    }
}
