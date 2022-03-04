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

package com.hms.hidraw.core

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.hms.hidraw.R
import com.hms.hidraw.util.ext.observe
import com.hms.hidraw.util.ext.observeEvent

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
