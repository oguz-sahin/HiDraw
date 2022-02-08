package com.huawei.hidraw.data.model

data class UserDetailModel(
    val email: String,
    val name: String,
    val userId: String,
    val participantDrawCount: Int,
    val createdDraw: Int
)
