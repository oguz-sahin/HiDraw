package com.huawei.hidraw.data.model

abstract class BaseDrawModel {
    abstract val name: String
    abstract val winnerCounts: String
    abstract val reserveCount: String
    abstract val startDate: String
    abstract val endDate: String
    abstract val description: String
}

data class InstagramDrawModel(
    override var name: String,
    override var winnerCounts: String,
    override var reserveCount: String,
    override var startDate: String,
    override var endDate: String,
    override var description: String,

    val url: String,
    val minTagCount: String,
    val eachUserOnlyOnce: Boolean,
    val screenRecord: Boolean
) : BaseDrawModel()

data class CustomDrawModel(
    override var name: String,
    override var winnerCounts: String,
    override var reserveCount: String,
    override var startDate: String,
    override var endDate: String,
    override var description: String,

    val participantNames: String
) : BaseDrawModel()


/**
 * Created by Oguz Sahin on 12/1/2021.
 */
data class DrawModel(
    val creatorId: String,
    val startDate: Long,
    val endDate: Long,
    val participantCount: Int,
    val winner: String,
    val drawName: String,
    val platform: String,
    val singleComment: Boolean,
    val minLabelCount: Int,
    val permenantUserCount: Int,
    val spareUserCount: Int,
    val status: Int,
    val description: String,
    val postUrl: String
    // creator photo
    // instagram post url background url

)