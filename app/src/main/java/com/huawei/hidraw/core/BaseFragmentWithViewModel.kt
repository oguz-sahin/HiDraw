package com.huawei.hidraw.core

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.huawei.hidraw.R
import com.huawei.hidraw.util.ext.observe
import com.huawei.hidraw.util.ext.observeEvent

/**
 * Created by Oguz Sahin on 10/26/2021.
 */
abstract class BaseFragmentWithViewModel<VB : ViewBinding, out VM : BaseViewModel>(
    inflate: Inflate<VB>
) : BaseFragment<VB>(inflate) {

    abstract val viewModel: VM

    private val loadingDialog: AlertDialog by lazy { createLoadingDialog() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEvent(viewModel.baseEvent, ::onViewEvent)
        observe(viewModel.loading, ::handleLoadingStatus)
    }

    private fun createLoadingDialog(): AlertDialog {
        val loadingView = LayoutInflater.from(requireContext()).inflate(R.layout.view_loading, null)
        return AlertDialog.Builder(requireContext())
            .setView(loadingView)
            .setCancelable(false)
            .create().apply {
                window?.setBackgroundDrawableResource(android.R.color.transparent)
            }
    }

    private fun handleLoadingStatus(isLoading: Boolean) {
        if (isLoading) loadingDialog.show() else loadingDialog.cancel()
    }

    private fun onViewEvent(event: BaseViewEvent) {
        when (event) {
            is BaseViewEvent.ShowSuccess -> showSuccess(event.message)

            is BaseViewEvent.ShowSuccessWithId -> showSuccess(event.message)

            is BaseViewEvent.ShowError -> showError(event.message)

            is BaseViewEvent.ShowErrorWithId -> showError(event.messageId)

            is BaseViewEvent.NavigateTo -> navigateDirections(event.directions)

            BaseViewEvent.NavigateBack -> navigateBack()
        }
    }
}
