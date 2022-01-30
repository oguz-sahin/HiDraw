package com.huawei.hidraw.ui.createdraw

import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.huawei.hidraw.R
import com.huawei.hidraw.core.BaseDialogFragment
import com.huawei.hidraw.core.BaseFragmentWithViewModel
import com.huawei.hidraw.data.model.CommonBasicResultModel
import com.huawei.hidraw.data.model.DialogModel
import com.huawei.hidraw.data.model.DrawModel
import com.huawei.hidraw.databinding.FragmentCreateDrawBinding
import com.huawei.hidraw.ui.CreateDrawViewState
import com.huawei.hidraw.ui.DrawTypes.CUSTOM
import com.huawei.hidraw.ui.DrawTypes.INSTAGRAM
import com.huawei.hidraw.util.ext.getContent
import com.huawei.hidraw.util.ext.getIntContent
import com.huawei.hidraw.util.ext.observe
import com.huawei.hidraw.vm.CreateDrawViewModel

class CreateDrawFragment :
    BaseFragmentWithViewModel<FragmentCreateDrawBinding, CreateDrawViewModel>(
        FragmentCreateDrawBinding::inflate
    ) {
    override val viewModel: CreateDrawViewModel by viewModels()

    private val materialDatePickerForStart = lazy {
        buildDatePicker(R.string.selectStartDate) { selectedStartDate ->
            viewModel.setSelectedStartDate(selectedStartDate)
        }
    }

    override fun initObserver() {
        observe(viewModel.createDrawFragmentViewState, ::setViewState)
    }

    private val materialDatePickerForEnd = lazy {
        buildDatePicker(R.string.selectEndDate) { selectedEndDate ->
            viewModel.setSelectedEndDate(selectedEndDate)
        }
    }

    override fun initListener() {

        with(binding) {

            btnStartDate.setOnClickListener {
                materialDatePickerForStart.value.show(childFragmentManager, "DATE")
            }

            btnEndDate.setOnClickListener {
                materialDatePickerForEnd.value.show(childFragmentManager, "DATE")
            }

            cpCustom.setOnClickListener {
                viewModel.setDrawType(CUSTOM)
            }

            cpInstagram.setOnClickListener {
                viewModel.setDrawType(INSTAGRAM)
            }
            btnCreateDraw.setOnClickListener {
                createDraw()
            }

            tvInputInfo.setOnClickListener {
                BaseDialogFragment(DialogModel(context!!, R.layout.dialog_input_info)).show(
                    childFragmentManager,
                    "InfoDialog"
                )
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

    private fun createDraw() {
        val values: DrawModel = contentFromInputs()
        val createDrawResult = viewModel.createDraw(values)
        showDialogWithResult(createDrawResult)
    }

    private fun contentFromInputs(): DrawModel {

        val startDate = if (materialDatePickerForStart.isInitialized()) {
            materialDatePickerForStart.value.selection as Long
        } else {
            System.currentTimeMillis()
        }

        val endDate = if (materialDatePickerForEnd.isInitialized()) {
            materialDatePickerForEnd.value.selection as Long
        } else {
            System.currentTimeMillis()
        }

        with(binding) {
            return DrawModel(
                "1",
                startDate,
                endDate,
                0,
                "",
                etDrawName.getContent(),
                INSTAGRAM.toString(),
                cbEachUserOnlyOnce.isChecked,
                etMinTagCount.getIntContent(),
                etWinnerCounts.getIntContent(),
                etReserveCount.getIntContent(),
                0,
                etDrawDescription.getContent(),
                etDrawUrl.getContent()
            )
        }
    } // TODO cbScreenRecord.isChecked ??

    private fun showDialogWithResult(result: CommonBasicResultModel<String>) {

        val title = if (result.passed)
            getString(R.string.success)
        else
            getString(R.string.common_error)

        BaseDialogFragment(
            DialogModel(
                context!!,
                R.layout.dialog_blank,
                dialogTitle = title,
                dialogMessage = result.info
            )
        ).show(
            childFragmentManager,
            "ResultDialog"
        )
    }

    private fun setViewState(viewState: CreateDrawViewState) {
        binding.viewState = viewState
        binding.executePendingBindings()
    }
}
