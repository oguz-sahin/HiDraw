package com.huawei.hidraw.data.repository

import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.model.DrawBodyModel
import com.huawei.hidraw.data.model.DrawModel

interface SaveDrawRepository {
    suspend fun saveDraw(drawBodyModel: DrawBodyModel): ResultWrapper<DrawModel>
}
