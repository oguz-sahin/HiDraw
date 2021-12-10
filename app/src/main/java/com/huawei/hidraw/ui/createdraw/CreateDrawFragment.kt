package com.huawei.hidraw.ui.createdraw

import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.MaterialDatePicker
import com.huawei.hidraw.R
import com.huawei.hidraw.core.BaseDialogFragment
import com.huawei.hidraw.core.BaseFragmentWithViewModel
import com.huawei.hidraw.data.model.CustomDrawModel
import com.huawei.hidraw.data.model.DialogModel
import com.huawei.hidraw.data.model.InstagramDrawModel
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

        with(binding) {

            btnStartDate.setOnClickListener {
                materialDatePickerForStart.show(childFragmentManager, "DATE")
            }

            btnEndDate.setOnClickListener {
                materialDatePickerForEnd.show(childFragmentManager, "DATE")
            }

            cpCustom.setOnClickListener {
                viewModel.setDrawType(CUSTOM)
            }

            cpInstagram.setOnClickListener {
                viewModel.setDrawType(INSTAGRAM)
            }
            btnCreateDraw.setOnClickListener {
                if (binding.viewState?.getCustomGroupVisibility() == View.VISIBLE)
                    createDrawWithCustomType()
                else
                    createDrawWithInstagramType()
            }

            tvInputInfo.setOnClickListener {
                BaseDialogFragment(DialogModel(context!!, R.layout.dialog_input_info)).show(
                    childFragmentManager,
                    "InfoDialog"
                )
            }

            cpGroupDrawType.setOnCheckedChangeListener { group, checkedId ->
                val chip: Chip? = group.findViewById(checkedId)
                chip?.let {chipView ->
                    Toast.makeText(context, chip.text, Toast.LENGTH_SHORT).show()
                } ?: kotlin.run {
                }
            }
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

    private fun createDrawWithInstagramType(){
        val data: InstagramDrawModel = getInstagramDrawContentFromInputs()
        // TODO MAKE THE REQUEST
    }
    private fun createDrawWithCustomType() {
        val data: CustomDrawModel = getCustomDrawContentFromInputs()
        // TODO MAKE THE REQUEST
    }


    private fun getInstagramDrawContentFromInputs(): InstagramDrawModel {

        with(binding) {
            return InstagramDrawModel(
                etDrawName.getContent(),
                etWinnerCounts.getContent(),
                etReserveCount.getContent(),
                btnStartDate.text.toString(),
                btnEndDate.text.toString(),
                etCustomDrawDescription.getContent(),
                etDrawUrl.getContent(),
                etMinTagCount.getContent(),
                cbEachUserOnlyOnce.isChecked,
                cbScreenRecord.isChecked
            )
        }
    }

    private fun getCustomDrawContentFromInputs(): CustomDrawModel {

        return with(binding) {
             CustomDrawModel(
                etDrawName.getContent(),
                etWinnerCounts.getContent(),
                etReserveCount.getContent(),
                btnStartDate.text.toString(),
                btnEndDate.text.toString(),
                etCustomDrawDescription.getContent(),
                etDrawParticipantNames.getContent()
            )
        }
    }

    private fun setViewState(viewState: CreateDrawViewState) {
        binding.viewState = viewState
        binding.executePendingBindings()
    }
}