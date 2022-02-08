package com.huawei.hidraw.data.datasource.remote

import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.model.DrawModel
import com.huawei.hidraw.data.model.UserDetailModel
import com.huawei.hidraw.data.model.UserModel
import com.huawei.hidraw.network.service.DrawService
import com.huawei.hidraw.network.service.UserService
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 12/2/2021.
 */
class ProfileRemoteDataSource @Inject constructor(
    private val drawService: DrawService,
    private val userService: UserService
) : BaseRemoteDataSource() {

    suspend fun getUser(): ResultWrapper<UserDetailModel> {
        return safeApiCall { userService.getUser() }
    }

    suspend fun getAttendedDrawsOfUser(): ResultWrapper<List<DrawModel>> {
        return safeApiCall { drawService.getAttendedDrawsOfUser() }
    }

    suspend fun getCreatedDrawsByUser(): ResultWrapper<List<DrawModel>> {
        return safeApiCall { drawService.getCreatedDrawsByUser() }
    }
}
