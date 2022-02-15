package com.huawei.hidraw.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.huawei.hidraw.core.BaseFragmentWithViewModel
import com.huawei.hidraw.data.model.DrawModel
import com.huawei.hidraw.databinding.FragmentHomeBinding
import com.huawei.hidraw.ui.adapter.draw.DrawAdapter
import com.huawei.hidraw.ui.home.HomeFragmentDirections.actionHomeFragmentToDrawDetailFragment
import com.huawei.hidraw.ui.home.HomeFragmentDirections.actionHomeFragmentToSignInFragment
import com.huawei.hidraw.util.ext.observe
import com.huawei.hidraw.vm.HomeViewModel
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
        observe(viewModel.activeDraws, ::loadDraws)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.isUserLogged().not()) navigateDirections(actionHomeFragmentToSignInFragment())
        initAdapter()
        viewModel.getActiveDraws()
    }

    private fun initAdapter() {
        drawAdapter.onDrawClicked = { drawId -> navigateDrawDetail(drawId) }
        binding.rvDraws.adapter = drawAdapter
    }

    private fun navigateDrawDetail(drawId: Long) {
        val action = actionHomeFragmentToDrawDetailFragment().setDrawId(drawId)
        navigateDirections(action)
    }

    private fun loadDraws(drawResponse: List<DrawModel>) {
        drawAdapter.draws = drawResponse
    }
}
