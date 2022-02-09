package com.huawei.hidraw.data.repository

import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.model.DrawDetailModel
import com.huawei.hidraw.data.model.ParticipateDrawBodyModel

/**
 * Created by Oguz Sahin on 12/13/2021.
 */
interface DrawDetailRepository {
    suspend fun getDrawById(drawId: Long): ResultWrapper<DrawDetailModel>
    suspend fun participateToDraw(participateDrawBodyModel: ParticipateDrawBodyModel): ResultWrapper<Any>
}
