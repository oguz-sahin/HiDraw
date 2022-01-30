package com.huawei.hidraw.data.repository

import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.ResultWrapper.Error
import com.huawei.hidraw.data.ResultWrapper.Success
import com.huawei.hidraw.data.datasource.remote.DrawDetailRemoteDataSource
import com.huawei.hidraw.data.mapper.DrawDetailMapper
import com.huawei.hidraw.data.model.DrawDetailModel
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 12/13/2021.
 */
class DrawDetailRepositoryImpl @Inject constructor(
    private val drawDetailRemoteDataSource: DrawDetailRemoteDataSource,
    private val authRepositoryImpl: AuthRepository
) : DrawDetailRepository {

    override suspend fun getDrawById(drawId: Long): ResultWrapper<DrawDetailModel> {
        val userId = authRepositoryImpl.getUserId()
        return when (val drawModelResult = drawDetailRemoteDataSource.getDrawById(drawId)) {
            is Error -> Error(drawModelResult.value)
            is Success -> Success(DrawDetailMapper(userId).map(drawModelResult.value))
        }
    }
}








