package com.huawei.hidraw.data.datasource.remote

import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.model.DrawModel
import com.huawei.hidraw.network.service.DrawService
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 12/13/2021.
 */
class DrawDetailRemoteDataSource @Inject constructor(
    private val drawService: DrawService
) : BaseRemoteDataSource() {

    suspend fun getDrawById(drawId: Long): ResultWrapper<DrawModel> {
        return safeApiCall { drawService.getDrawById(drawId) }
    }
}
