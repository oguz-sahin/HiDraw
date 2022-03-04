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

package com.hms.hidraw.util.ext

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.hms.hidraw.util.AppPermission
import com.hms.hidraw.util.ManageExternalContracts

fun Fragment.hasPermission(permission: AppPermission): Boolean {
    return when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> requireContext().checkSelfPermission(
            permission.name
        ) == PackageManager.PERMISSION_GRANTED
        else -> true
    }
}

fun Fragment.hasPermissions(vararg permissions: AppPermission): Boolean {
    return permissions.all { hasPermission(it) }
}

fun Fragment.getContentLauncher(onResult: (Uri?) -> Unit) =
    registerForActivityResult(ActivityResultContracts.GetContent(), onResult)

fun Fragment.getStartActivityForResultLauncher(onResult: (ActivityResult) -> Unit) =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult(), onResult)

fun Fragment.getManageExternalStorageResultLauncher(onResult: (Boolean) -> Unit) =
    registerForActivityResult(ManageExternalContracts(), onResult)

fun Fragment.getPermissionResultLauncher(
    permission: AppPermission,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: ((AppPermission) -> Unit)? = null,
    onPermissionDeniedPermanently: ((AppPermission) -> Unit)? = null
) = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
    when {
        isGranted -> onPermissionGranted.invoke()
        shouldShowRequestPermissionRationale(permission.name).not() -> onPermissionDeniedPermanently?.invoke(
            permission
        )
        else -> onPermissionDenied?.invoke(permission)
    }
}

fun Fragment.goSettings() {
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", requireContext().packageName, null)
    }.run {
        startActivity(this)
    }
}

@RequiresApi(Build.VERSION_CODES.R)
fun checkManageExternalPermission(): Boolean =
    Environment.isExternalStorageManager()
