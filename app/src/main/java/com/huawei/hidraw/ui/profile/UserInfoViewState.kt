package com.huawei.hidraw.ui.profile

import com.huawei.hidraw.data.model.UserDetailModel
import com.huawei.hidraw.data.model.UserModel

/**
 * Created by Oguz Sahin on 12/2/2021.
 */
data class UserInfoViewState(
    val userInfo: UserDetailModel
) {

    fun getUserName() = userInfo.user.name

    fun getCreatedDrawCount() = userInfo.createdDrawCount.toString()

    fun getAttendedDrawCount() = userInfo.participantDrawCount.toString()

    companion object {
        fun initial() = UserInfoViewState(
            userInfo = UserDetailModel(
                user = UserModel(),
                participantDrawCount = 0,
                createdDrawCount = 0
            )
        )
    }
}
