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

package com.hms.hidraw.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.hms.hidraw.core.BaseFragmentWithViewModel
import com.hms.hidraw.databinding.FragmentHomeBinding
import com.hms.hidraw.ui.adapter.draw.DrawAdapter
import com.hms.hidraw.ui.home.HomeFragmentDirections.actionHomeFragmentToDrawDetailFragment
import com.hms.hidraw.ui.home.HomeFragmentDirections.actionHomeFragmentToSignInFragment
import com.hms.hidraw.ui.profile.DrawListViewState
import com.hms.hidraw.util.ext.executeWithAction
import com.hms.hidraw.util.ext.observe
import com.hms.hidraw.util.ext.observeEvent
import com.hms.hidraw.vm.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragmentWithViewModel<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var drawAdapter: DrawAdapter

    override fun initObserver() {
        observe(viewModel.drawListViewState, ::setDrawListViewState)
        observeEvent(viewModel.isRefreshingStatus, ::setRefreshStatus)
    }

    override fun initListener() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getActiveDraws()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.isUserLogged().not()) navigateDirections(actionHomeFragmentToSignInFragment())
        handleDeeplink()
        initAdapter()
        viewModel.getActiveDraws()
    }

    private fun handleDeeplink() {
        getDeeplinkDrawId()?.let {
            if (viewModel.isNavigatedDrawIdChanged(it)) {
                viewModel.setDrawIdIfHasDeeplink(it)
                navigateDrawDetail(it.toLong())
            }
        }
    }

    private fun getDeeplinkDrawId(): Int? {
        return activity?.intent?.extras?.get(DRAW_ID_DEEPLINK_KEY) as? Int
    }

    private fun initAdapter() {
        drawAdapter.onDrawClicked = { drawId -> navigateDrawDetail(drawId) }
        binding.rvDraws.adapter = drawAdapter
    }

    private fun navigateDrawDetail(drawId: Long) {
        val action = actionHomeFragmentToDrawDetailFragment().setDrawId(drawId)
        navigateDirections(action)
    }

    private fun setDrawListViewState(drawListViewState: DrawListViewState) {
        binding.executeWithAction {
            viewState = drawListViewState
        }
        drawAdapter.draws = drawListViewState.drawList
    }

    private fun setRefreshStatus(isRefresh: Boolean) {
        binding.swipeRefresh.isRefreshing = isRefresh
    }

    companion object {
        private const val DRAW_ID_DEEPLINK_KEY = "drawId"
    }
}
