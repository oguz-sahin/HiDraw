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

package com.huawei.hidraw.ui.adapter.draw

import android.content.Context
import com.huawei.hidraw.R
import com.huawei.hidraw.core.BaseViewState
import com.huawei.hidraw.data.model.DrawModel
import com.huawei.hidraw.data.model.DrawStatusTypes
import com.huawei.hidraw.util.ext.convertTimeStampWithFormat
import com.huawei.hidraw.util.ext.getDifferenceDayByNow
import com.huawei.hidraw.util.ext.getServerUrl

data class DrawItemViewState(
    private val draw: DrawModel
) : BaseViewState() {

    fun getDrawDescription() = draw.description

    fun getDrawName() = draw.title

    fun getImageUrl() = draw.photoUrl?.getServerUrl()

    fun getRemainingVisibility() =
        getViewVisibility(isViewVisible = draw.status == DrawStatusTypes.ACTIVE.value)

    fun getDrawRemainingDay(context: Context): String? {
        return if (draw.status) {
            val remainingDay = draw.endDate.getDifferenceDayByNow()
            return if (remainingDay < 30) "$remainingDay${context.getString(R.string.day_abbreviation)}"
            else "${remainingDay / 30}${context.getString(R.string.month_abbreviation)}"
        } else null
    }

    fun getDrawEndDate() = draw.endDate.convertTimeStampWithFormat()
}
