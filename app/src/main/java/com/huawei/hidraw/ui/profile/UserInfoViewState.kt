package com.huawei.hidraw.ui.profile

import com.huawei.hidraw.data.model.UserDetailModel

/**
 * Created by Oguz Sahin on 12/2/2021.
 */
data class UserInfoViewState(
    val userInfo: UserDetailModel
) {

    fun getUserName() = userInfo.name

    fun getCreatedDrawCount() = userInfo.createdDraw.toString()

    fun getAttendedDrawCount() = userInfo.participantDrawCount.toString()

    companion object {
        fun initial() = UserInfoViewState(
            userInfo = UserDetailModel(
                email = "",
                name = "",
                userId = "",
                participantDrawCount = 0,
                createdDraw = 0
            )
        )
    }
}
