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
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.hms.hidraw.core.BaseFragmentWithViewModel
import com.hms.hidraw.data.model.DrawModel
import com.hms.hidraw.databinding.FragmentDrawListBinding
import com.hms.hidraw.ui.adapter.draw.DrawAdapter
import com.hms.hidraw.ui.profile.ProfileFragmentDirections.actionProfileFragmentToDrawDetailFragment
import com.hms.hidraw.util.ext.executeWithAction
import com.hms.hidraw.util.ext.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DrawListFragment : BaseFragmentWithViewModel<FragmentDrawListBinding, DrawListViewModel>(
    FragmentDrawListBinding::inflate
) {

    @Inject
    lateinit var drawAdapter: DrawAdapter

    override val viewModel: DrawListViewModel by viewModels()

    override fun initObserver() {
        observe(viewModel.draws, ::setDrawList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private fun setAdapter() {
        drawAdapter.onDrawClicked = ::navigateDrawDetail
        binding.rvDrawList.adapter = drawAdapter
    }

    private fun setDrawList(drawList: List<DrawModel>) {
        drawAdapter.draws = drawList
        setViewState(drawList)
    }

    private fun navigateDrawDetail(drawId: Long) {
        val action = actionProfileFragmentToDrawDetailFragment().setDrawId(drawId)
        navigateDirections(action)
    }

    private fun setViewState(drawList: List<DrawModel>) {
        binding.executeWithAction {
            viewState = DrawListViewState(drawList)
        }
    }

    companion object {
        const val DRAW_LIST_TYPE = "DRAW_LIST_TYPE"
        fun newInstance(drawListType: DrawListTypes) = DrawListFragment().apply {
            arguments = bundleOf(DRAW_LIST_TYPE to drawListType.name)
        }
    }
}
