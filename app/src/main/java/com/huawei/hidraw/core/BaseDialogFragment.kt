package com.huawei.hidraw.core

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.huawei.hidraw.data.model.DialogModel

class BaseDialogFragment(private val dialogModel: DialogModel) :
    DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder: AlertDialog.Builder
        with(dialogModel) {

            dialogContext.let {
                builder = AlertDialog.Builder(dialogContext)
            }

            dialogTitle?.let {
                builder.setTitle(it.toString())
            }

            dialogMessage?.let {
                builder.setMessage(it)
            }

            positiveButtonClickListener?.let { positiveButtonClickListener ->
                builder.setPositiveButton(positiveButtonText.toString()) { _, _ ->
                    positiveButtonClickListener.invoke()
                }
            }

            negativeButtonClickListener?.let { negativeButtonClickListener ->
                builder.setPositiveButton(negativeButtonText.toString()) { _, _ ->
                    negativeButtonClickListener.invoke()
                }
            }

            builder.setCancelable(isCancelable)
            builder.setView(dialogViewId)

            return builder.create()
        }
    }
}