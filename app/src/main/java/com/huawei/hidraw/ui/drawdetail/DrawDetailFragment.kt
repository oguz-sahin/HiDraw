package com.huawei.hidraw.ui.drawdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.huawei.hidraw.core.BaseFragmentWithViewModel
import com.huawei.hidraw.databinding.FragmentDrawDetailBinding
import com.huawei.hidraw.ui.adapter.drawwinner.DrawWinnerAdapter
import com.huawei.hidraw.ui.drawdetail.DrawDetailEvent.NavigateToDrawResultPage
import com.huawei.hidraw.ui.drawdetail.DrawDetailFragmentDirections.actionDrawDetailFragmentToDrawResultFragment
import com.huawei.hidraw.util.ext.executeWithAction
import com.huawei.hidraw.util.ext.observe
import com.huawei.hidraw.util.ext.observeEvent
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
        observeEvent(viewModel.drawDetailEvent, ::handleEvent)
    }

    override fun initListener() {
        binding.btnAction.setOnClickListener {
            viewModel.onActionButtonClicked()
        }
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

    private fun handleEvent(drawDetailEvent: DrawDetailEvent) {
        when (drawDetailEvent) {
            NavigateToDrawResultPage -> navigateToResultPage()
        }
    }


    private fun navigateToResultPage() {
        val isScreenRecordTake = viewModel.getScreenRecordStatus()
        val drawId = viewModel.getDrawId()
        val direction = actionDrawDetailFragmentToDrawResultFragment()
            .setIsScreenRecordTake(isScreenRecordTake)
            .setDrawId(drawId)
        navigateDirections(direction)
    }


    private fun setViewState(drawDetailViewState: DrawDetailViewState) {
        binding.executeWithAction {
            viewState = drawDetailViewState
            winnersAdapter.winners = drawDetailViewState.getWinnersItemViewState()
            substitutesAdapter.winners = drawDetailViewState.getSubstitutesItemViewState()
        }
    }
}
