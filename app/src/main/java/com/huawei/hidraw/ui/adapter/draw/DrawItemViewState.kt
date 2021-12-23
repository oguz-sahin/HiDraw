package com.huawei.hidraw.ui.adapter.draw

import android.content.Context
import android.view.View
import com.huawei.hidraw.R
import com.huawei.hidraw.data.model.DrawModel
import com.huawei.hidraw.data.model.DrawStatusTypes.ACTIVE
import com.huawei.hidraw.util.ext.convertToDate
import com.huawei.hidraw.util.ext.getDifferenceDayByNow

/**
 * Created by Oguz Sahin on 12/2/2021.
 */
data class DrawItemViewState(
    private val draw: DrawModel
) {

    fun getDrawDescription() = draw.description

    fun getDrawNamea() = draw.drawName

    fun getRemainingVisibility() = if (draw.status == ACTIVE.value) View.VISIBLE else View.GONE

    fun getDrawRemainingDay(context: Context): String? {
        return if (draw.status == ACTIVE.value) {
            val remainingDay = draw.endDate.getDifferenceDayByNow()
            return if (remainingDay < 30) "$remainingDay${context.getString(R.string.day_abbreviation)}"
            else "${remainingDay / 30}${context.getString(R.string.month_abbreviation)}"
        } else null
    }

    fun getDrawEndDate() = draw.endDate.convertToDate()
}
