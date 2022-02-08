package com.huawei.hidraw.data.repository

import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.model.DrawModel
import com.huawei.hidraw.data.model.UserDetailModel
import com.huawei.hidraw.data.model.UserModel

/**
 * Created by Oguz Sahin on 12/2/2021.
 */
interface ProfileRepository {

    suspend fun getUser(): ResultWrapper<UserDetailModel>

    suspend fun getAttendedDrawsOfUser(): ResultWrapper<List<DrawModel>>

    suspend fun getCreatedDrawsByUser(): ResultWrapper<List<DrawModel>>
}
