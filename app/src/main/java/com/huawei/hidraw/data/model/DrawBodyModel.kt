package com.huawei.hidraw.data.model

data class DrawBodyModel(
    val title: String,
    val description: String,
    val postUrl: String,
    val photoUrl: String,
    val startDate: Long,
    val endDate: Long,
    val winnerCount: Int,
    val substituteCount: Int,
    val singleComment: Boolean,
    val screenRecord: Boolean,
    val status: Boolean,
    val creator: UserModel
)
