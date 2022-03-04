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

package com.hms.hidraw.vm

import android.net.Uri
import android.text.Editable
import androidx.lifecycle.viewModelScope
import com.hms.hidraw.R
import com.hms.hidraw.core.BaseViewModel
import com.hms.hidraw.data.model.DrawBodyModel
import com.hms.hidraw.data.model.DrawStatusTypes
import com.hms.hidraw.data.model.UserModel
import com.hms.hidraw.data.repository.AuthRepository
import com.hms.hidraw.data.repository.SaveDrawRepository
import com.hms.hidraw.ui.createdraw.DrawViewState
import com.hms.hidraw.util.ext.getContent
import com.hms.hidraw.util.manager.FileManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateDrawViewModel @Inject constructor(
    private val fileManager: FileManager,
    private val authRepositoryImpl: AuthRepository,
    private val saveDrawRepositoryImpl: SaveDrawRepository
) : BaseViewModel() {

    private val _drawViewState = MutableStateFlow(value = DrawViewState.initial())
    val drawViewState get() = _drawViewState.asStateFlow()

    fun setContent(contentUri: Uri?) {
        contentUri?.let {
            updateViewState { currentViewState ->
                currentViewState.copy(photoUrl = it)
            }
        }
    }

    fun setDrawTitle(title: Editable?) {
        updateViewState { currentViewState ->
            currentViewState.copy(title = title.getContent())
        }
    }

    fun setDrawDescription(description: Editable?) {
        updateViewState { currentViewState ->
            currentViewState.copy(description = description.getContent())
        }
    }

    fun setWinnerCount(winnerCount: Editable?) {
        updateViewState { currentViewState ->
            currentViewState.copy(winnerCount = winnerCount.getContent())
        }
    }

    fun setSubstituteCount(substituteCount: Editable?) {
        updateViewState { currentViewState ->
            currentViewState.copy(substituteCount = substituteCount.getContent())
        }
    }

    fun setSelectedStartDate(startDateText: String, startDate: Long) {
        updateViewState { currentViewState ->
            currentViewState.copy(startDateText = startDateText, startDate = startDate)
        }
    }

    fun setSelectedEndDate(endDateText: String, endDate: Long) {
        updateViewState { currentViewState ->
            currentViewState.copy(endDateText = endDateText, endDate = endDate)
        }
    }

    fun setPostUrl(postUrl: Editable?) {
        updateViewState { currentViewState ->
            currentViewState.copy(postUrl = postUrl.getContent())
        }
    }

    fun setTakeScreenRecordStatus(isTakenScreenRecord: Boolean) {
        updateViewState { currentViewState ->
            currentViewState.copy(isTakenScreenRecord = isTakenScreenRecord)
        }
    }

    fun setCountEachUserOnlyOnceStatus(isCountEachUserOnlyOnce: Boolean) {
        updateViewState { currentViewState ->
            currentViewState.copy(isUserCountOnlyOnce = isCountEachUserOnlyOnce)
        }
    }

    private fun updateViewState(function: (currentViewState: DrawViewState) -> DrawViewState) {
        _drawViewState.update(function)
    }

    fun createDraw() {
        if (checkInputsValidation().not()) return

        viewModelScope.launch {
            val drawBodyModel = getDrawBodyModelForRemote()
            makeNetworkRequest(
                requestFunc = { saveDrawRepositoryImpl.saveDraw(drawBodyModel) },
                onSuccess = {
                    showSuccess(R.string.draw_created_successfuly)
                    navigateBack()
                }
            )
        }
    }

    private fun checkInputsValidation(): Boolean {
        return when {
            drawViewState.value.title.isBlank() -> {
                showErrorWithId(R.string.please_enter_draw_title)
                false
            }
            drawViewState.value.description.isBlank() -> {
                showErrorWithId(R.string.please_enter_draw_description)
                false
            }
            drawViewState.value.winnerCount.isBlank() -> {
                showErrorWithId(R.string.please_enter_winner_count)
                false
            }
            drawViewState.value.substituteCount.isBlank() -> {
                showErrorWithId(R.string.please_enter_substitute_count)
                false
            }

            isValidWinnerCount().not() -> {
                showErrorWithId(R.string.please_enter_valid_winner_count)
                false
            }

            isSubstituteCountMoreWinnerCount() -> {
                showErrorWithId(R.string.substitute_count_must_not_more_from_winner_count)
                false
            }

            drawViewState.value.startDateText.isBlank() -> {
                showErrorWithId(R.string.please_select_start_date)
                false
            }

            drawViewState.value.endDateText.isBlank() -> {
                showErrorWithId(R.string.please_select_end_date)
                false
            }

            isStartDateAfterEndDate() -> {
                showErrorWithId(R.string.star_date_must_not_be_after_or_same_end_date)
                false
            }

            drawViewState.value.postUrl.isBlank() -> {
                showErrorWithId(R.string.please_enter_url)
                false
            }

            else -> true
        }
    }

    private fun isValidWinnerCount() = drawViewState.value.winnerCount.toInt() > 0

    private fun isSubstituteCountMoreWinnerCount(): Boolean {
        return drawViewState.value.winnerCount.toInt() < drawViewState.value.substituteCount.toInt()
    }

    private fun isStartDateAfterEndDate(): Boolean {
        val startDate = Date(drawViewState.value.startDate)
        val endDate = Date(drawViewState.value.endDate)
        return startDate.after(endDate) || startDate == endDate
    }

    private suspend fun getDrawBodyModelForRemote() = with(drawViewState.value) {
        val base64Photo = fileManager.convertUriToBas64(photoUrl)
        DrawBodyModel(
            title = title,
            description = description,
            photoUrl = base64Photo,
            postUrl = postUrl,
            startDate = startDate,
            endDate = endDate,
            winnerCount = winnerCount.toInt(),
            substituteCount = substituteCount.toInt(),
            singleComment = isUserCountOnlyOnce,
            screenRecord = isTakenScreenRecord,
            status = DrawStatusTypes.ACTIVE.value,
            creator = UserModel(userId = authRepositoryImpl.getUserId())
        )
    }
}
