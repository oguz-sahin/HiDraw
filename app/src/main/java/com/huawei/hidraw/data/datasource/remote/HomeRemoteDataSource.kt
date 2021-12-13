package com.huawei.hidraw.data.datasource.remote

import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.model.DrawModel
import com.huawei.hidraw.network.service.DrawService
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 12/1/2021.
 */
class HomeRemoteDataSource @Inject constructor(
    private val drawService: DrawService
) : BaseRemoteDataSource() {

    suspend fun getActiveDraws(): ResultWrapper<List<DrawModel>> {
        return safeApiCall { drawService.getActiveDraws() }
    }

}