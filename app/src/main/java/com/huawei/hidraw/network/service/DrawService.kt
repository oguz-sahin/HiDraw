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


    companion object {
        const val GET_ACTIVE_DRAWS_REQUEST_PATH = "draw/active"
        const val GET_CREATED_DRAW_REQUEST_PATH = "draw/createdDraw"
        const val GET_ATTENDED_DRAW_REQUEST_PATH = "participant"
        const val DRAW_REQUEST_PATH = "draw"
        const val PARTICIPANT_REQUEST_PATH = "participant"
    }
}
