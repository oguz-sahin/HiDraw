package com.huawei.hidraw.ui.home

import androidx.fragment.app.viewModels
import com.huawei.hidraw.core.BaseFragmentWithViewModel
import com.huawei.hidraw.databinding.FragmentHomeBinding
import com.huawei.hidraw.vm.HomeViewModel


class HomeFragment : BaseFragmentWithViewModel<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel: HomeViewModel by viewModels()


}