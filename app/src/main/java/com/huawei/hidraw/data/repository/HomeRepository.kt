package com.huawei.hidraw.data.repository

import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.model.DrawModel

/**
 * Created by Oguz Sahin on 12/1/2021.
 */
interface HomeRepository {
    suspend fun getActiveDraws(): ResultWrapper<List<DrawModel>>
}
