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
