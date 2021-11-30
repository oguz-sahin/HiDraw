package com.huawei.hidraw.ui.createdraw

import androidx.fragment.app.viewModels
import com.huawei.hidraw.core.BaseFragmentWithViewModel
import com.huawei.hidraw.databinding.FragmentCreateDrawBinding
import com.huawei.hidraw.vm.CreateDrawViewModel

class CreateDrawFragment:
    BaseFragmentWithViewModel<FragmentCreateDrawBinding, CreateDrawViewModel>(
        FragmentCreateDrawBinding::inflate
    ) {
    override val viewModel: CreateDrawViewModel by viewModels()
}