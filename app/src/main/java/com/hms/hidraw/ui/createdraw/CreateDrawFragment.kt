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

package com.hms.hidraw.ui.createdraw

import android.app.AlertDialog
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.hms.hidraw.R
import com.hms.hidraw.core.BaseFragmentWithViewModel
import com.hms.hidraw.databinding.FragmentCreateDrawBinding
import com.hms.hidraw.ui.createdraw.CalendarTypes.END
import com.hms.hidraw.ui.createdraw.CalendarTypes.START
import com.hms.hidraw.util.AppPermission
import com.hms.hidraw.util.ext.*
import com.hms.hidraw.vm.CreateDrawViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateDrawFragment :
    BaseFragmentWithViewModel<FragmentCreateDrawBinding, CreateDrawViewModel>(
        FragmentCreateDrawBinding::inflate
    ) {
    override val viewModel: CreateDrawViewModel by viewModels()

    private val permissionExplanationDialog by lazy { createPermissionExplanationDialog() }

    private val permissionMustGiveExplanationDialogForPermanentlyDenied by lazy { createPermissionExplanationDialogForPermanentlyDenied() }

    private val getContentLauncher = getContentLauncher { contentUri ->
        viewModel.setContent(contentUri)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private val manageExternalStorageLauncher =
        getManageExternalStorageResultLauncher { handlePermissionStatusAndGetContentForAndroid11AndAbove() }

    private val readExternalStoragePermissionResultLauncher = getPermissionResultLauncher(
        permission = AppPermission.ReadExternalStorage,
        onPermissionDenied = { permissionExplanationDialog.show() },
        onPermissionGranted = { openGalleryForImage() },
        onPermissionDeniedPermanently = { permissionMustGiveExplanationDialogForPermanentlyDenied.show() }
    )

    private val materialDatePickerForStart by lazy {
        buildDatePicker(
            title = R.string.selectStartDate,
            calendarType = START
        ) { selectedStartDateText, selectedStartDate ->
            viewModel.setSelectedStartDate(selectedStartDateText, selectedStartDate)
        }
    }

    private val materialDatePickerForEnd by lazy {
        buildDatePicker(
            title = R.string.selectEndDate,
            calendarType = END
        ) { selectedEndDateText, selectedEndDate ->
            viewModel.setSelectedEndDate(selectedEndDateText, selectedEndDate)
        }
    }

    override fun initObserver() {
        viewLifecycleOwner.collectLast(viewModel.drawViewState, ::setViewState)
    }

    override fun initListener() {
        with(binding) {

            btnSelectImage.setOnClickListener {
                handlePermissionStatusAndGetContentByBuildVersion()
            }

            etDrawName.doAfterTextChanged {
                viewModel.setDrawTitle(it)
            }

            etDrawDescription.doAfterTextChanged {
                viewModel.setDrawDescription(it)
            }

            etWinnerCounts.doAfterTextChanged {
                viewModel.setWinnerCount(it)
            }

            etSubstituteCount.doAfterTextChanged {
                viewModel.setSubstituteCount(it)
            }

            btnStartDate.setOnClickListener {
                materialDatePickerForStart.show(childFragmentManager, START_DATE_TAG)
            }

            btnEndDate.setOnClickListener {
                materialDatePickerForEnd.show(childFragmentManager, END_DATE_TAG)
            }

            etDrawUrl.doAfterTextChanged {
                viewModel.setPostUrl(it)
            }

            cbScreenRecord.setOnCheckedChangeListener { _, isChecked ->
                viewModel.setTakeScreenRecordStatus(isChecked)
            }

            cbEachUserOnlyOnce.setOnCheckedChangeListener { _, isChecked ->
                viewModel.setCountEachUserOnlyOnceStatus(isChecked)
            }

            btnCreateDraw.setOnClickListener {
                viewModel.createDraw()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            etWinnerCounts.clearTransformationMethod()
            etSubstituteCount.clearTransformationMethod()
        }
    }

    private fun handlePermissionStatusAndGetContentByBuildVersion() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            handlePermissionStatusAndGetContentForAndroid11AndAbove()
        } else {
            handlePermissionStatusAndGetContentForAndroid11Below()
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun handlePermissionStatusAndGetContentForAndroid11AndAbove() {
        if (checkManageExternalPermission()) {
            openGalleryForImage()
        } else {
            manageExternalStorageLauncher.launch("")
        }
    }

    private fun handlePermissionStatusAndGetContentForAndroid11Below() {
        if (hasPermission(AppPermission.ReadExternalStorage)) {
            openGalleryForImage()
        } else {
            readExternalStoragePermissionResultLauncher.launch(AppPermission.ReadExternalStorage.name)
        }
    }

    private fun openGalleryForImage() {
        getContentLauncher.launch("image/*")
    }

    private fun createPermissionExplanationDialog() = AlertDialog.Builder(requireContext())
        .setTitle(getString(R.string.permission_need))
        .setMessage(getString(R.string.content_permission_explanation))
        .setCancelable(true)
        .setPositiveButton(getString(R.string.give_permission)) { _, _ ->
            handlePermissionStatusAndGetContentByBuildVersion()
        }
        .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
        .create()

    private fun createPermissionExplanationDialogForPermanentlyDenied() =
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.permission_need))
            .setMessage(getString(R.string.content_permission_explanation))
            .setCancelable(true)
            .setPositiveButton(getString(R.string.give_permission)) { _, _ ->
                goSettings()
            }.create()

    private fun buildDatePicker(
        @StringRes title: Int,
        calendarType: CalendarTypes,
        onPositiveClickListener: (dateText: String, date: Long) -> Unit
    ): MaterialDatePicker<*> {
        val calendarConstraints = CalendarConstraints.Builder()
            .setValidator(
                getDatePickerSelectionValidator(calendarType)
            ).build()
        val materialDatePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(title)
            .setCalendarConstraints(calendarConstraints)
            .build()
        materialDatePicker.addOnPositiveButtonClickListener {
            onPositiveClickListener.invoke(materialDatePicker.headerText, it)
        }
        return materialDatePicker
    }

    private fun setViewState(drawViewState: DrawViewState) {
        binding.executeWithAction {
            this.drawViewState = drawViewState
        }
    }

    private fun TextInputEditText.clearTransformationMethod() {
        transformationMethod = null
    }

    private fun getDatePickerSelectionValidator(calendarType: CalendarTypes): CalendarConstraints.DateValidator {
        return when (calendarType) {
            START -> DateValidatorPointForward.now()
            END -> NextDaySelectionValidator()
        }
    }

    companion object {
        const val START_DATE_TAG = "START_DATE_TAG"
        const val END_DATE_TAG = "END_DATE_TAG"
    }
}
