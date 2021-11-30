package com.huawei.hidraw.ui.createdraw

import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.huawei.hidraw.R
import com.huawei.hidraw.core.BaseFragmentWithViewModel
import com.huawei.hidraw.databinding.FragmentCreateDrawBinding
import com.huawei.hidraw.ui.CreateDrawViewState
import com.huawei.hidraw.ui.DrawTypes.CUSTOM
import com.huawei.hidraw.ui.DrawTypes.INSTAGRAM
import com.huawei.hidraw.util.ext.getContent
import com.huawei.hidraw.util.ext.observe
import com.huawei.hidraw.vm.CreateDrawViewModel

class CreateDrawFragment :
    BaseFragmentWithViewModel<FragmentCreateDrawBinding, CreateDrawViewModel>(
        FragmentCreateDrawBinding::inflate
    ) {
    override val viewModel: CreateDrawViewModel by viewModels()

    private val materialDatePickerForStart: MaterialDatePicker<*> by lazy {
        buildDatePicker(R.string.selectStartDate) { selectedStartDate ->
            viewModel.setSelectedStartDate(selectedStartDate)
        }
    }
    private val materialDatePickerForEnd: MaterialDatePicker<*> by lazy {
        buildDatePicker(R.string.selectEndDate) { selectedEndDate ->
            viewModel.setSelectedEndDate(selectedEndDate)
        }
    }

    override fun initObserver() {
        observe(viewModel.createDrawFragmentViewState, ::setViewState)
    }

    override fun initListener() {

        binding.btnStartDate.setOnClickListener {
            materialDatePickerForStart.show(childFragmentManager, "DATE")
        }

        binding.btnEndDate.setOnClickListener {
            materialDatePickerForEnd.show(childFragmentManager, "DATE")
        }

        binding.cpCustom.setOnClickListener {
            viewModel.setDrawType(CUSTOM)
        }

        binding.cpInstagram.setOnClickListener {
            viewModel.setDrawType(INSTAGRAM)
        }

        binding.btnCreateDraw.setOnClickListener {
            if (binding.viewState?.getCustomGroupVisibility() == View.VISIBLE)
                createDrawWithCustomType()
            else
                createDrawWithInstagramType()
        }
    }

    private fun buildDatePicker(
        @StringRes title: Int,
        onPositiveClickListener: (date: String) -> Unit
    ): MaterialDatePicker<*> {

        val materialDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText(title).build()

        materialDatePicker.addOnPositiveButtonClickListener {
            onPositiveClickListener.invoke(materialDatePicker.headerText)
        }
        return materialDatePicker
    }

    private fun createDrawWithInstagramType() {
        with(binding) {
            etDrawName.getContent()
            etWinnerCounts.getContent()
            etReserveCount.getContent()
            btnStartDate.text
            btnEndDate.text
            etDrawUrl.getContent()
            etInstagramDrawDescription.getContent()
            etMinTagCount.getContent()
            cbEachUserOnlyOnce.isChecked
            cbScreenRecord.isChecked
        }
    }

    private fun createDrawWithCustomType() {
        with(binding) {
            etDrawName.getContent()
            etWinnerCounts.getContent()
            etReserveCount.getContent()
            Log.e("date", btnStartDate.text.toString())
            btnStartDate.text
            btnEndDate.text
            etCustomDrawDescription.getContent()
            etDrawParticipantNames.getContent()
        }
    }


    private fun setViewState(viewState: CreateDrawViewState) {
        binding.viewState = viewState
        binding.executePendingBindings()
    }
}