package com.huawei.hidraw.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.huawei.hidraw.core.BaseFragmentWithViewModel
import com.huawei.hidraw.databinding.FragmentProfileBinding
import com.huawei.hidraw.vm.ProfileViewModel


class ProfileFragment : BaseFragmentWithViewModel<FragmentProfileBinding, ProfileViewModel>(
    FragmentProfileBinding::inflate
) {
    override val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}