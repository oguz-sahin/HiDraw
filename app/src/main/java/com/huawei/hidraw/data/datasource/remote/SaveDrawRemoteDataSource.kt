package com.huawei.hidraw.data.datasource.remote

import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.model.DrawBodyModel
import com.huawei.hidraw.data.model.DrawModel
import com.huawei.hidraw.network.service.DrawService
import javax.inject.Inject

class SaveDrawRemoteDataSource @Inject constructor(
    private val drawService: DrawService
) : BaseRemoteDataSource() {

    suspend fun saveDraw(drawBodyModel: DrawBodyModel): ResultWrapper<DrawModel> {
        return safeApiCall { drawService.saveDraw(drawBodyModel) }
    }
}
