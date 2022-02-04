package com.huawei.hidraw.ui.createdraw

import android.net.Uri

data class DrawViewState(
    val title: String,
    val description: String,
    val winnerCount: String,
    val substituteCount: String,
    val startDateText: String,
    val endDateText: String,
    val startDate: Long,
    val endDate: Long,
    val postUrl: String,
    val photoUrl: Uri,
    val isTakenScreenRecord: Boolean,
    val isUserCountOnlyOnce: Boolean
) {
    companion object {
        fun initial(): DrawViewState = DrawViewState(
            title = "",
            description = "",
            winnerCount = "",
            substituteCount = "",
            startDateText = "",
            endDateText = "",
            startDate = 0,
            endDate = 0,
            photoUrl = Uri.parse(DEFAULT_DRAW_BACKGROUND_URI_STRING),
            postUrl = "",
            isTakenScreenRecord = false,
            isUserCountOnlyOnce = false
        )

        private const val DEFAULT_DRAW_BACKGROUND_URI_STRING =
            "android.resource://com.huawei.hidraw/drawable/bg_default_draw"
    }

}
