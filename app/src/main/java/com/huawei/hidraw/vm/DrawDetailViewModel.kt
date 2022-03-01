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
import com.huawei.hidraw.data.model.*
import com.huawei.hidraw.data.repository.AuthRepository
import com.huawei.hidraw.data.repository.DrawDetailRepository
import com.huawei.hidraw.ui.drawdetail.DrawDetailEvent
import com.huawei.hidraw.ui.drawdetail.DrawDetailFragmentArgs
import com.huawei.hidraw.ui.drawdetail.DrawDetailViewState
import com.huawei.hidraw.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 11/24/2021.
 */

@HiltViewModel
class DrawDetailViewModel @Inject constructor(
    private val drawDetailRepositoryImpl: DrawDetailRepository,
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private var drawDetailModel = DrawDetailModel.initial()

    private val _drawDetailViewState = MutableLiveData(DrawDetailViewState(drawDetailModel))
    val drawDetailViewState: LiveData<DrawDetailViewState> get() = _drawDetailViewState

    private val _drawDetailEvent = MutableLiveData<Event<DrawDetailEvent>>()
    val drawDetailEvent get() = _drawDetailEvent

    init {
        val args = DrawDetailFragmentArgs.fromSavedStateHandle(savedStateHandle)
        getDrawDetail(args.drawId)
    }

    private fun getDrawDetail(drawId: Long) {
        makeNetworkRequest(
            requestFunc = { drawDetailRepositoryImpl.getDrawById(drawId) },
            onSuccess = {
                drawDetailModel = it
                _drawDetailViewState.postValue(DrawDetailViewState(it))
            }
        )
    }

    fun onActionButtonClicked() {
        when {
            isUserAlreadyParticipant() -> Unit
            canUserParticipateToDraw() -> participateToDraw()
            canStartDraw() -> setEvent(
                _drawDetailEvent,
                DrawDetailEvent.NavigateToDrawResultPage
            )
        }
    }

    private fun canStartDraw() = isDrawActive() && drawDetailModel.createdUser

    private fun participateToDraw() {
        val drawModel = DrawModel.initial().copy(id = drawDetailModel.draw.id)
        val userModel = UserModel(userId = authRepository.getUserId())
        val participateDrawBodyModel = ParticipateDrawBodyModel(draw = drawModel, user = userModel)
        makeNetworkRequest(
            requestFunc = { drawDetailRepositoryImpl.participateToDraw(participateDrawBodyModel = participateDrawBodyModel) },
            onSuccess = {
                drawDetailModel = drawDetailModel.copy(userAttended = true)
                _drawDetailViewState.value = DrawDetailViewState(drawDetailModel = drawDetailModel)
            }
        )
    }

    private fun canUserParticipateToDraw(): Boolean {
        return isDrawActive() && drawDetailModel.userAttended.not() && drawDetailModel.createdUser.not()
    }

    private fun isDrawActive() = drawDetailModel.draw.status == DrawStatusTypes.ACTIVE.value

    private fun isUserAlreadyParticipant() = drawDetailModel.userAttended

    fun getScreenRecordStatus() = drawDetailModel.draw.screenRecord

    fun getDrawId() = drawDetailModel.draw.id
}
