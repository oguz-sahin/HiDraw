package com.huawei.hidraw.ui.createdraw

import com.google.android.material.datepicker.CalendarConstraints
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime
import java.time.ZoneOffset

@Parcelize
class NextDaySelectionValidator : CalendarConstraints.DateValidator {
    override fun isValid(date: Long): Boolean {
        val nextDay = LocalDateTime.now().atZone(ZoneOffset.UTC).toInstant().toEpochMilli()
        return date > nextDay
    }
}
