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

package com.hms.hidraw.data.datasource.local

import android.content.SharedPreferences
import com.hms.hidraw.util.ext.defaultForType
import com.hms.hidraw.util.ext.getValue
import com.hms.hidraw.util.ext.setValue
import javax.inject.Inject

class PrefDataSource @Inject constructor(private val sharedPref: SharedPreferences) {

    var userId: String
        get() = sharedPref.getValue(USER_ID)
        set(value) {
            sharedPref.setValue(USER_ID, value)
        }

    fun isUserLogged(): Boolean = userId != defaultForType<String>()

    companion object {
        private const val USER_ID = "USER_ID"
    }
}
