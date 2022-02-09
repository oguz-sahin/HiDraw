package com.huawei.hidraw.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.huawei.hidraw.R
import com.huawei.hidraw.core.BaseFragmentWithViewModel
import com.huawei.hidraw.databinding.FragmentProfileBinding
import com.huawei.hidraw.ui.adapter.draw.UserDrawFragmentStateAdapter
import com.huawei.hidraw.ui.profile.DrawListTypes.USER_ATTENDED
import com.huawei.hidraw.util.ext.executeWithAction
import com.huawei.hidraw.util.ext.observe
import com.huawei.hidraw.vm.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragmentWithViewModel<FragmentProfileBinding, ProfileViewModel>(
    FragmentProfileBinding::inflate
) {
    override val viewModel: ProfileViewModel by viewModels()

    override fun initObserver() {
        observe(viewModel.userInfoViewState, ::setUserInfoViewState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        viewModel.getProfileDetail()
        initTabLayout()
    }

    private fun initAdapter() {
        UserDrawFragmentStateAdapter(this).also {
            binding.vpDraws.adapter = it
        }
    }

    private fun initTabLayout() {
        TabLayoutMediator(binding.tlDraws, binding.vpDraws) { tab, position ->
            tab.text = if (position == USER_ATTENDED.pageIndex) getString(R.string.my_attended)
            else getString(R.string.my_created)
        }.attach()
    }

    private fun setUserInfoViewState(viewState: UserInfoViewState) {
        binding.executeWithAction {
            userInfoViewState = viewState
        }
    }
}
