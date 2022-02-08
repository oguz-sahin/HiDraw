package com.huawei.hidraw.data.model

/**
 * Created by Oguz Sahin on 12/1/2021.
 */
data class DrawModel(
    val id: Long = 0,
    val title: String,
    val description: String,
    val postUrl: String,
    val photoUrl: String?,
    val startDate: Long,
    val endDate: Long,
    val participantCount: Int = 0,
    val winnerCount: Int,
    val substituteCount: Int,
    val singleComment: Boolean,
    val screenRecord: Boolean,
    val status: Boolean,
    val creator: UserModel
)
