package com.huawei.hidraw.data.model

abstract class DrawModel {
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
) : DrawModel()

data class CustomDrawModel(
    override var name: String,
    override var winnerCounts: String,
    override var reserveCount: String,
    override var startDate: String,
    override var endDate: String,
    override var description: String,

    val participantNames: String
) : DrawModel()


