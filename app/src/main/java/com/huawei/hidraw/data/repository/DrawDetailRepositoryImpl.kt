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








