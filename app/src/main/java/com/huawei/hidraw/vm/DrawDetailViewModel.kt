package com.huawei.hidraw.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.huawei.hidraw.core.BaseViewModel
import com.huawei.hidraw.data.model.*
import com.huawei.hidraw.data.repository.AuthRepository
import com.huawei.hidraw.data.repository.DrawDetailRepository
import com.huawei.hidraw.ui.drawdetail.DrawDetailEvent
import com.huawei.hidraw.ui.drawdetail.DrawDetailFragmentArgs
import com.huawei.hidraw.ui.drawdetail.DrawDetailViewState
import com.huawei.hidraw.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
        viewModelScope.launch {
            makeNetworkRequest(
                requestFunc = {
                    drawDetailRepositoryImpl.getDrawById(drawId)
                },
                onSuccess = {
                    drawDetailModel = it
                    _drawDetailViewState.postValue(DrawDetailViewState(it))
                }
            )
        }
    }

    fun onActionButtonClicked() {
        when {
            isUserAlreadyParticipant() -> Unit
            canUserParticipateToDraw() -> participateToDraw()
            canStartDraw() -> setEvent(
                _drawDetailEvent,
                DrawDetailEvent.NavigateToDrawPage
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

}
