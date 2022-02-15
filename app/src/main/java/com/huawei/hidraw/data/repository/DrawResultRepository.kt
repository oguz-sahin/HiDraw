package com.huawei.hidraw.data.repository

import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.model.DrawResultModel

interface DrawResultRepository {
    suspend fun getDrawResult(drawId: Long): ResultWrapper<DrawResultModel>
}
