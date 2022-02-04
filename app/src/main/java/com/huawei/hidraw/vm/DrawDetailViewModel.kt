package com.huawei.hidraw.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.huawei.hidraw.core.BaseViewModel
import com.huawei.hidraw.data.repository.DrawDetailRepository
import com.huawei.hidraw.ui.drawdetail.DrawDetailFragmentArgs
import com.huawei.hidraw.ui.drawdetail.DrawDetailFragmentViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 11/24/2021.
 */

@HiltViewModel
class DrawDetailViewModel @Inject constructor(
    private val drawDetailRepositoryImpl: DrawDetailRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _drawDetailFragmentViewState = MutableLiveData<DrawDetailFragmentViewState>()
    val drawDetailFragmentViewState: LiveData<DrawDetailFragmentViewState> get() = _drawDetailFragmentViewState


    init {
        //for the type safety
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
                    _drawDetailFragmentViewState.postValue(DrawDetailFragmentViewState(it))
                }
            )
        }
    }
}
