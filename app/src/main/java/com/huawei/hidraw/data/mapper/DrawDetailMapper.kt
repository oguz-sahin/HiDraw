package com.huawei.hidraw.data.mapper

import com.huawei.hidraw.data.model.DrawDetailModel
import com.huawei.hidraw.data.model.DrawModel

/**
 * Created by Oguz Sahin on 12/28/2021.
 */
class DrawDetailMapper(private val userId: String) : Mapper<DrawModel, DrawDetailModel>() {
    override fun map(from: DrawModel): DrawDetailModel = with(from) {
        DrawDetailModel(
            creatorId,
            startDate,
            endDate,
            participantCount,
            winner,
            drawName,
            permenantUserCount,
            spareUserCount,
            status,
            description,
            postUrl,
            userId == creatorId
        )
    }

}
