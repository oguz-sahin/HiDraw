package com.huawei.hidraw.data.repository

import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.datasource.remote.DrawResultRemoteDataSource
import com.huawei.hidraw.data.model.DrawResultModel
import javax.inject.Inject

class DrawResultRepositoryImpl @Inject constructor(
    private val drawResultRemoteDataSource: DrawResultRemoteDataSource
) : DrawResultRepository {

    override suspend fun getDrawResult(drawId: Long): ResultWrapper<DrawResultModel> {
        return drawResultRemoteDataSource.getDrawResult(drawId)
    }
}
