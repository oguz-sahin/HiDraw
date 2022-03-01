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

import com.huawei.hidraw.data.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Oguz Sahin on 11/29/2021.
 */
interface DrawService {

    @GET(GET_ACTIVE_DRAWS_REQUEST_PATH)
    suspend fun getActiveDraws(): BaseResponseModel<List<DrawModel>>

    @GET(GET_ATTENDED_DRAW_REQUEST_PATH)
    suspend fun getAttendedDrawsOfUser(): BaseResponseModel<List<DrawModel>>

    @GET(GET_CREATED_DRAW_REQUEST_PATH)
    suspend fun getCreatedDrawsByUser(): BaseResponseModel<List<DrawModel>>

    @GET(DRAW_REQUEST_PATH)
    suspend fun getDrawById(
        @Query("drawId") drawId: Long
    ): BaseResponseModel<DrawDetailModel>

    @POST(DRAW_REQUEST_PATH)
    suspend fun saveDraw(
        @Body draw: DrawBodyModel
    ): BaseResponseModel<DrawModel>

    @POST(PARTICIPANT_REQUEST_PATH)
    suspend fun participateToDraw(@Body participant: ParticipateDrawBodyModel): BaseResponseModel<Any>

    @GET(DRAW_RESULT_REQUEST_PATH)
    suspend fun getDrawResult(@Query("drawId") drawId: Long): BaseResponseModel<DrawResultModel>

    companion object {
        const val GET_ACTIVE_DRAWS_REQUEST_PATH = "draw/active"
        const val GET_CREATED_DRAW_REQUEST_PATH = "draw/createdDraw"
        const val GET_ATTENDED_DRAW_REQUEST_PATH = "participant"
        const val DRAW_REQUEST_PATH = "draw"
        const val PARTICIPANT_REQUEST_PATH = "participant"
        const val DRAW_RESULT_REQUEST_PATH = "result/complete"
    }
}
