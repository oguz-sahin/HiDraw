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
