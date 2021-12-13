package com.huawei.hidraw.data.model

/**
 * Created by Oguz Sahin on 11/15/2021.
 */
data class UserModel(
    val createdDraw: Int = 0,
    val email: String = "",
    val igUserName: String = "",
    val isPremium: Boolean = false,
    val joinedDraw: Int = 0,
    val name: String = "",
    val notificationStatus: Boolean = false,
    val pushTokenId: Long = 0L,
    val userId: String = ""
//user profile photo mevzusu
)
