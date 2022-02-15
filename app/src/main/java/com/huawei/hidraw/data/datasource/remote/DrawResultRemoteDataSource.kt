package com.huawei.hidraw.data.datasource.remote

import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.model.DrawResultModel
import com.huawei.hidraw.network.service.DrawService
import javax.inject.Inject

class DrawResultRemoteDataSource @Inject constructor(
    private val drawService: DrawService
) : BaseRemoteDataSource() {

    suspend fun getDrawResult(drawId: Long): ResultWrapper<DrawResultModel> {
        return safeApiCall { drawService.getDrawResult(drawId) }
    }


}
