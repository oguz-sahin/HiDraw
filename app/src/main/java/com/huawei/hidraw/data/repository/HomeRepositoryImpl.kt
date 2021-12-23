package com.huawei.hidraw.data.repository

import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.datasource.remote.HomeRemoteDataSource
import com.huawei.hidraw.data.model.DrawModel
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 12/1/2021.
 */
class HomeRepositoryImpl @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource
) : HomeRepository {
    override suspend fun getActiveDraws(): ResultWrapper<List<DrawModel>> {
        return homeRemoteDataSource.getActiveDraws()
    }
}
