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
