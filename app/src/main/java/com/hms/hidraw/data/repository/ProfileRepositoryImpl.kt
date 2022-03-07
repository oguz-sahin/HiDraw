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

package com.hms.hidraw.data.repository

import com.hms.hidraw.data.ResultWrapper
import com.hms.hidraw.data.datasource.remote.ProfileRemoteDataSource
import com.hms.hidraw.data.model.AttendedDrawModel
import com.hms.hidraw.data.model.DrawModel
import com.hms.hidraw.data.model.UserDetailModel
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileRemoteDataSource: ProfileRemoteDataSource
) : ProfileRepository {

    override suspend fun getUser(): ResultWrapper<UserDetailModel> {
        return profileRemoteDataSource.getUser()
    }

    override suspend fun getAttendedDrawsOfUser(): ResultWrapper<List<AttendedDrawModel>> {
        return profileRemoteDataSource.getAttendedDrawsOfUser()
    }

    override suspend fun getCreatedDrawsByUser(): ResultWrapper<List<DrawModel>> {
        return profileRemoteDataSource.getCreatedDrawsByUser()
    }
}
