package com.huawei.hidraw.ui.drawdetail

import androidx.fragment.app.viewModels
import com.huawei.hidraw.core.BaseFragmentWithViewModel
import com.huawei.hidraw.databinding.FragmentDrawDetailBinding
import com.huawei.hidraw.vm.DrawDetailViewModel


class DrawDetailFragment :
    BaseFragmentWithViewModel<FragmentDrawDetailBinding, DrawDetailViewModel>(
        FragmentDrawDetailBinding::inflate
    ) {
    override val viewModel: DrawDetailViewModel by viewModels()


}