package com.huawei.hidraw.network.service

import com.huawei.hidraw.data.model.BaseResponseModel
import com.huawei.hidraw.data.model.DrawModel
import com.huawei.hidraw.network.NetworkUtils.GET_ACTIVE_DRAWS_REQUEST_PATH
import com.huawei.hidraw.network.NetworkUtils.GET_ATTENDED_DRAW_REQUEST_PATH
import com.huawei.hidraw.network.NetworkUtils.GET_CREATED_DRAW_REQUEST_PATH
import retrofit2.http.GET

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

}