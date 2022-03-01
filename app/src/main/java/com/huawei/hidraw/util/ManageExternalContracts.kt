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

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.RequiresApi

class ManageExternalContracts : ActivityResultContract<Any, Boolean>() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun createIntent(context: Context, input: Any): Intent {
        return try {
            Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                addCategory("android.intent.category.DEFAULT")
                data = Uri.parse(String.format("package:%s", context.packageName))
            }
        } catch (e: Exception) {
            Intent().apply {
                action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun parseResult(resultCode: Int, intent: Intent?) = true
}
