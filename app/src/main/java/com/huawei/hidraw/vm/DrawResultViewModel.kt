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

package com.huawei.hidraw.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.huawei.hidraw.core.BaseViewModel
import com.huawei.hidraw.data.model.DrawResultModel
import com.huawei.hidraw.data.repository.DrawResultRepository
import com.huawei.hidraw.ui.adapter.drawresult.DrawResultAdapterModel
import com.huawei.hidraw.ui.adapter.drawresult.DrawResultAdapterModel.TitleItem
import com.huawei.hidraw.ui.adapter.drawresult.DrawResultAdapterModel.WinnerItem
import com.huawei.hidraw.ui.adapter.title.ItemTitleViewState
import com.huawei.hidraw.ui.drawresult.DrawResultEvent
import com.huawei.hidraw.ui.drawresult.DrawResultFragmentArgs
import com.huawei.hidraw.util.Event
import com.huawei.hidraw.util.ext.getItemWinnerViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DrawResultViewModel @Inject constructor(
    private val drawResultRepositoryImpl: DrawResultRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _drawResultEvent = MutableLiveData<Event<DrawResultEvent>>()
    val drawResultEvent: LiveData<Event<DrawResultEvent>> get() = _drawResultEvent

    private val _drawResult = MutableLiveData<List<DrawResultAdapterModel>>()
    val drawResult: LiveData<List<DrawResultAdapterModel>> get() = _drawResult

    private var drawId: Long = -1

    init {
        val args = DrawResultFragmentArgs.fromSavedStateHandle(savedStateHandle)
        drawId = args.drawId
        if (args.isScreenRecordTake) {
            setEvent(_drawResultEvent, DrawResultEvent.CheckPermissionsForScreenRecord)
        } else {
            getDrawResult()
        }
    }

    fun getDrawResult() {
        makeNetworkRequest(
            requestFunc = { drawResultRepositoryImpl.getDrawResult(drawId) },
            onSuccess = ::setDrawResult,
            onError = { navigateBack() }
        )
    }

    private fun setDrawResult(drawResultModel: DrawResultModel) {
        val winnersItemViewStates = drawResultModel.winners.getItemWinnerViewState()
        val substitutesItemViewStates = drawResultModel.substitutes.getItemWinnerViewState()

        val drawResult = mutableListOf<DrawResultAdapterModel>().also { list ->
            list.add(TitleItem(itemTitleViewState = ItemTitleViewState(title = "Winners")))

            winnersItemViewStates.forEach {
                list.add(WinnerItem(itemWinnerViewState = it))
            }

            list.add(TitleItem(ItemTitleViewState(title = "Substitutes")))

            substitutesItemViewStates.forEach {
                list.add(WinnerItem(it))
            }
        }

        _drawResult.postValue(drawResult)
    }

    fun getDrawId() = drawId
}
