package com.huawei.hidraw.ui.adapter.draw

import android.content.Context
import com.huawei.hidraw.R
import com.huawei.hidraw.core.BaseViewState
import com.huawei.hidraw.data.model.DrawModel
import com.huawei.hidraw.data.model.DrawStatusTypes
import com.huawei.hidraw.util.ext.convertTimeStampWithFormat
import com.huawei.hidraw.util.ext.getDifferenceDayByNow

/**
 * Created by Oguz Sahin on 12/2/2021.
 */
data class DrawItemViewState(
    private val draw: DrawModel
) : BaseViewState() {

    fun getDrawDescription() = draw.description

    fun getDrawName() = draw.title

    fun getImageUrl() = draw.photoUrl

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
