package com.huawei.hidraw.data.model

/**
 * Created by Oguz Sahin on 12/27/2021.
 */
data class DrawDetailModel(
    val id: Long,
    val title: String,
    val description: String,
    val postUrl: String,
    val photoUrl: String?,
    val startDate: Long,
    val endDate: Long,
    val participantCount: Int,
    val winnerCount: Int,
    val substituteCount: Int,
    val singleComment: Boolean,
    val screenRecord: Boolean,
    val status: Boolean,
    val winners: List<UserModel>?,
    val substitutes: List<UserModel>?,
    val createdUser: Boolean,
    val userAttended: Boolean
)


