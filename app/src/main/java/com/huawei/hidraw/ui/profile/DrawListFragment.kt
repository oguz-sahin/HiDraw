package com.huawei.hidraw.ui.profile

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.huawei.hidraw.core.BaseFragmentWithViewModel
import com.huawei.hidraw.data.model.DrawModel
import com.huawei.hidraw.databinding.FragmentDrawListBinding
import com.huawei.hidraw.ui.adapter.draw.DrawAdapter
import com.huawei.hidraw.ui.profile.ProfileFragmentDirections.actionProfileFragmentToDrawDetailFragment
import com.huawei.hidraw.util.ext.executeWithAction
import com.huawei.hidraw.util.ext.observe
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
