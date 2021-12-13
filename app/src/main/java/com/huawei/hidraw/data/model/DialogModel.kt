package com.huawei.hidraw.data.model

import android.content.Context
import com.huawei.hidraw.R

data class DialogModel (
    val dialogContext: Context,
    val dialogViewId: Int,
    val isCancelable: Boolean = true,

    val dialogTitle: R.string? = null,
    val dialogMessage: Int? = null,
    val positiveButtonText: R.string? = null,
    val positiveButtonClickListener: (() -> Unit)? = null,
    val negativeButtonText: R.string? = null,
    val negativeButtonClickListener: (() -> Unit)? = null
)