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

package com.huawei.hidraw.util

import android.Manifest
import androidx.annotation.StringRes
import com.huawei.hidraw.R

sealed class AppPermission(
    val name: String,
    @StringRes val explanationMessageId: Int = R.string.common_permission_explanation_message
) {

    object WriteExternalStorage : AppPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    object ReadExternalStorage : AppPermission(Manifest.permission.READ_EXTERNAL_STORAGE)

    object RecordAudio : AppPermission(Manifest.permission.RECORD_AUDIO)
}
