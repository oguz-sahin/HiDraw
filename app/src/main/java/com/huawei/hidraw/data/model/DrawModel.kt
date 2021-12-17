package com.huawei.hidraw.data.model

import com.google.gson.annotations.Expose

/**
 * Created by Oguz Sahin on 12/1/2021.
 */
data class DrawModel(
    val creatorId: String,
    val startDate: Long = 0L, // string
    val endDate: Long = 0L, //string
    val participantCount: Int = 0,
    val winner: String = "",
    val drawName: String,
    val platform: String,
    val singleComment: Boolean = false,
    val minLabelCount: Int = 0,
    val permenantUserCount: Int,
    val spareUserCount: Int,
    val status: Int,
    val description: String,
    val postUrl: String = "",
    @Expose
    val participantNames: String = ""
    // creator photo
    // instagram post url background url

)