package com.huawei.hidraw.data.model

/**
 * Created by Oguz Sahin on 12/27/2021.
 */
data class DrawDetailModel(
    val creatorId: String,
    val startDate: Long = 0L, // string
    val endDate: Long = 0L, // string
    val participantCount: Int = 0,
    val winner: String = "",
    val drawName: String,
    val permenantUserCount: Int,
    val spareUserCount: Int,
    val status: Int,
    val description: String,
    val postUrl: String = "",
    val isDrawCreatorMe: Boolean = false
)
