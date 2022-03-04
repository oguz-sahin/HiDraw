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

package com.hms.hidraw.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.hms.hidraw.R
import com.hms.hidraw.core.BaseFragmentWithViewModel
import com.hms.hidraw.databinding.FragmentProfileBinding
import com.hms.hidraw.ui.adapter.draw.UserDrawFragmentStateAdapter
import com.hms.hidraw.ui.profile.DrawListTypes.USER_ATTENDED
import com.hms.hidraw.util.ext.executeWithAction
import com.hms.hidraw.util.ext.observe
import com.hms.hidraw.vm.ProfileViewModel
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
