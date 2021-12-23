package com.huawei.hidraw.ui.profile

import com.huawei.hidraw.data.model.UserModel

/**
 * Created by Oguz Sahin on 12/2/2021.
 */
data class UserInfoViewState(
    val userInfo: UserModel? = null
) {

    fun getUserName() = userInfo?.name ?: ""

    fun getCreatedDrawCount() = userInfo?.createdDraw?.toString() ?: ""

    fun getAttendedDrawCount() = userInfo?.joinedDraw?.toString() ?: ""

    companion object {
        fun initial() = UserInfoViewState()
    }
}
