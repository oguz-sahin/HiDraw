package com.huawei.hidraw.ui.drawdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.huawei.hidraw.core.BaseFragmentWithViewModel
import com.huawei.hidraw.databinding.FragmentDrawDetailBinding
import com.huawei.hidraw.util.ext.observe
import com.huawei.hidraw.vm.DrawDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrawDetailFragment :
    BaseFragmentWithViewModel<FragmentDrawDetailBinding, DrawDetailViewModel>(
        FragmentDrawDetailBinding::inflate
    ) {
    override val viewModel: DrawDetailViewModel by viewModels()


    override fun initObserver() {
        observe(viewModel.drawDetailFragmentViewState, ::setViewState)
    }


    private fun setViewState(drawDetailFragmentViewState: DrawDetailFragmentViewState) {
        with(binding) {
            viewState = drawDetailFragmentViewState
            executePendingBindings()
        }
    }
}
