package com.huawei.hidraw.data.repository

import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.datasource.remote.SaveDrawRemoteDataSource
import com.huawei.hidraw.data.model.DrawBodyModel
import com.huawei.hidraw.data.model.DrawModel
import javax.inject.Inject

class SaveDrawRepositoryImpl @Inject constructor(
    private val saveDrawRemoteDataSource: SaveDrawRemoteDataSource
) : SaveDrawRepository {

    override suspend fun saveDraw(drawBodyModel: DrawBodyModel): ResultWrapper<DrawModel> {
        return saveDrawRemoteDataSource.saveDraw(drawBodyModel)
    }

}
