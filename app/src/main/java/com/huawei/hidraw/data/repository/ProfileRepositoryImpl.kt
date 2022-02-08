package com.huawei.hidraw.data.repository

import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.datasource.remote.ProfileRemoteDataSource
import com.huawei.hidraw.data.model.DrawModel
import com.huawei.hidraw.data.model.UserDetailModel
import com.huawei.hidraw.data.model.UserModel
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 12/2/2021.
 */
class ProfileRepositoryImpl @Inject constructor(
    private val profileRemoteDataSource: ProfileRemoteDataSource
) : ProfileRepository {

    override suspend fun getUser(): ResultWrapper<UserDetailModel> {
        return profileRemoteDataSource.getUser()
    }

    override suspend fun getAttendedDrawsOfUser(): ResultWrapper<List<DrawModel>> {
        return profileRemoteDataSource.getAttendedDrawsOfUser()
    }

    override suspend fun getCreatedDrawsByUser(): ResultWrapper<List<DrawModel>> {
        return profileRemoteDataSource.getCreatedDrawsByUser()
    }
}
