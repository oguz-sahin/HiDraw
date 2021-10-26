package com.huawei.hidraw.core

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.huawei.hidraw.R
import com.huawei.hidraw.util.ext.observeEvent

/**
 * Created by Oguz Sahin on 10/26/2021.
 */
abstract class BaseFragmentWithViewModel<VB : ViewBinding, out VM : BaseViewModel>(
    inflate: Inflate<VB>
) : BaseFragment<VB>(inflate) {

    abstract val viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEvent(viewModel.baseEvent, ::onViewEvent)
    }


    private fun onViewEvent(event: BaseViewEvent) {
        when (event) {
            is BaseViewEvent.ShowSuccess -> showSuccess(event.message)

            is BaseViewEvent.ShowSuccessWithId -> showSuccess(event.message)

            is BaseViewEvent.ShowError -> showError(event.message)

            is BaseViewEvent.ShowConnectionError -> showError(getString(R.string.connection_error))

            is BaseViewEvent.ShowGeneralError -> showError(getString(R.string.general_error))

            is BaseViewEvent.NavigateTo -> navigateDirections(event.directions)
        }
    }

}