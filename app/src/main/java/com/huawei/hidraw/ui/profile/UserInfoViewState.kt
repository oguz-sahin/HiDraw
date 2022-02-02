package com.huawei.hidraw.ui.profile

import com.huawei.hidraw.data.model.UserModel

/**
 * Created by Oguz Sahin on 12/2/2021.
 */
data class UserInfoViewState(
    val userInfo: UserModel? = null
) {

    fun getUserName() = userInfo?.name ?: ""

    fun getCreatedDrawCount() = "12" // TODO("Backendden data istenecek)

    fun getAttendedDrawCount() = "12" // TODO("Backendden data istenecek)

    companion object {
        fun initial() = UserInfoViewState()
    }
}
