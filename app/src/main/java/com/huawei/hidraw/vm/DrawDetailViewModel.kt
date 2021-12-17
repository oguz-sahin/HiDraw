package com.huawei.hidraw.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.huawei.hidraw.core.BaseViewModel
import com.huawei.hidraw.data.repository.DrawDetailRepositoryImpl
import com.huawei.hidraw.ui.drawdetail.DrawDetailFragmentViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 11/24/2021.
 */

@HiltViewModel
class DrawDetailViewModel @Inject constructor(
    private val drawDetailRepositoryImpl: DrawDetailRepositoryImpl
) : BaseViewModel() {


    private val _drawDetailFragmentViewState = MutableLiveData<DrawDetailFragmentViewState>()
    val drawDetailFragmentViewState: LiveData<DrawDetailFragmentViewState> get() = _drawDetailFragmentViewState


    fun getDrawDetail(drawId: Long) {
        viewModelScope.launch {
            makeNetworkRequest(
                requestFunc = {
                    drawDetailRepositoryImpl.getDrawById(drawId)
                },
                onSuccess = {
                    _drawDetailFragmentViewState.postValue(DrawDetailFragmentViewState(it))
                })
        }
    }


}
