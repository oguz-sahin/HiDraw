/*
 * Copyright 2022. Explore in HMS. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hms.hidraw.ui.createdraw

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
