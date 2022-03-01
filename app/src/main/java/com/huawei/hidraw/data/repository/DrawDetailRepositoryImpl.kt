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
import com.huawei.hidraw.data.datasource.remote.DrawDetailRemoteDataSource
import com.huawei.hidraw.data.model.DrawDetailModel
import com.huawei.hidraw.data.model.ParticipateDrawBodyModel
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 12/13/2021.
 */
class DrawDetailRepositoryImpl @Inject constructor(
    private val drawDetailRemoteDataSource: DrawDetailRemoteDataSource
) : DrawDetailRepository {

    override suspend fun getDrawById(drawId: Long): ResultWrapper<DrawDetailModel> {
        return drawDetailRemoteDataSource.getDrawById(drawId)
    }

    override suspend fun participateToDraw(participateDrawBodyModel: ParticipateDrawBodyModel): ResultWrapper<Any> {
        return drawDetailRemoteDataSource.participateToDraw(participateDrawBodyModel)
    }
}
