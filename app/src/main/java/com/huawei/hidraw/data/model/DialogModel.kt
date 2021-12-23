package com.huawei.hidraw.data.model

import android.content.Context
import com.huawei.hidraw.R

data class DialogModel(
    val dialogContext: Context,
    val dialogViewId: Int,
    val isCancelable: Boolean = true,

    val dialogTitle: String? = null,
    val dialogMessage: String? = null,
    val positiveButtonText: R.string? = null,
    val positiveButtonClickListener: (() -> Unit)? = null,
    val negativeButtonText: R.string? = null,
    val negativeButtonClickListener: (() -> Unit)? = null
)
