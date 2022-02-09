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
) {
    companion object {
        fun initial() = DrawModel(
            id = 0,
            title = "",
            description = "",
            postUrl = "",
            photoUrl = "",
            startDate = 0,
            endDate = 0,
            participantCount = 0,
            winnerCount = 0,
            substituteCount = 0,
            singleComment = false,
            screenRecord = false,
            status = false,
            creator = UserModel()
        )
    }
}
