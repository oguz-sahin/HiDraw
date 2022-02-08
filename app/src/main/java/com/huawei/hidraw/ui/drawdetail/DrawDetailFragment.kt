package com.huawei.hidraw.ui.drawdetail

import androidx.fragment.app.viewModels
import com.huawei.hidraw.core.BaseFragmentWithViewModel
import com.huawei.hidraw.databinding.FragmentDrawDetailBinding
import com.huawei.hidraw.util.ext.executeWithAction
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
        observe(viewModel.drawDetailViewState, ::setViewState)
    }


    private fun setViewState(drawDetailViewState: DrawDetailViewState) {
        binding.executeWithAction {
            viewState = drawDetailViewState
        }
    }
}
