package com.huawei.hidraw.ui.drawdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.huawei.hidraw.core.BaseFragmentWithViewModel
import com.huawei.hidraw.databinding.FragmentDrawDetailBinding
import com.huawei.hidraw.util.ext.executeWithAction
import com.huawei.hidraw.util.ext.observe
import com.huawei.hidraw.vm.DrawDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DrawDetailFragment :
    BaseFragmentWithViewModel<FragmentDrawDetailBinding, DrawDetailViewModel>(
        FragmentDrawDetailBinding::inflate
    ) {

    override val viewModel: DrawDetailViewModel by viewModels()

    @Inject
    lateinit var winnersAdapter: DrawWinnerAdapter

    @Inject
    lateinit var substitutesAdapter: DrawWinnerAdapter


    override fun initObserver() {
        observe(viewModel.drawDetailViewState, ::setViewState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        with(binding) {
            rvWinners.adapter = winnersAdapter
            rvSubstitutes.adapter = substitutesAdapter
        }
    }


    private fun setViewState(drawDetailViewState: DrawDetailViewState) {
        binding.executeWithAction {
            viewState = drawDetailViewState
            winnersAdapter.winners = drawDetailViewState.getWinnersItemViewState()
            substitutesAdapter.winners = drawDetailViewState.getSubstitutesItemViewState()
        }
    }
}
