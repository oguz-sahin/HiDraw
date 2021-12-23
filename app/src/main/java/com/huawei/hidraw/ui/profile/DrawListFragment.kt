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
import com.huawei.hidraw.util.ext.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DrawListFragment : BaseFragmentWithViewModel<FragmentDrawListBinding, DrawListViewModel>(
    FragmentDrawListBinding::inflate
) {

    @Inject
    lateinit var drawAdapter: DrawAdapter

    private var drawListType: DrawListTypes? = null

    override val viewModel: DrawListViewModel by viewModels()

    override fun initObserver() {
        observe(viewModel.draws, ::setDrawList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDrawListType()
        setAdapter()
        viewModel.getDrawsByType(drawListType)
    }

    private fun setDrawListType() {
        arguments?.getString(DRAW_LIST_TYPE)?.let {
            drawListType = DrawListTypes.valueOf(it)
        }
    }

    private fun setAdapter() {
        drawAdapter.onDrawClicked = { drawId ->
            val action = actionProfileFragmentToDrawDetailFragment().setDrawId(drawId)
            navigateDirections(action)
        }
        binding.rvDrawList.adapter = drawAdapter
    }

    private fun setDrawList(drawList: List<DrawModel>) {
        val isDrawListEmpty = drawList.isEmpty()
        if (isDrawListEmpty.not()) {
            drawAdapter.draws = drawList
        }
        setViewState(isDrawListEmpty)
    }

    private fun setViewState(isDrawListEmpty: Boolean) {
        with(binding) {
            viewState = DrawListViewState(isDrawListEmpty)
            executePendingBindings()
        }
    }

    companion object {
        private const val DRAW_LIST_TYPE = "DRAW_LIST_TYPE"
        fun newInstance(drawListType: DrawListTypes) = DrawListFragment().apply {
            arguments = bundleOf(DRAW_LIST_TYPE to drawListType.name)
        }
    }
}
