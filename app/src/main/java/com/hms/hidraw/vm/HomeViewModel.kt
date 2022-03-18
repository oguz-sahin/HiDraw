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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hms.hidraw.core.BaseViewModel
import com.hms.hidraw.data.repository.AuthRepository
import com.hms.hidraw.data.repository.HomeRepository
import com.hms.hidraw.ui.profile.DrawListViewState
import com.hms.hidraw.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val authRepositoryImpl: AuthRepository
) : BaseViewModel() {

    private var navigatedDrawId: Long? = null

    private val _drawListViewState = MutableLiveData<DrawListViewState>()
    val drawListViewState: LiveData<DrawListViewState> get() = _drawListViewState


    private val _isRefreshingStatus = MutableLiveData<Event<Boolean>>()
    val isRefreshingStatus: LiveData<Event<Boolean>> get() = _isRefreshingStatus

    fun setDrawIdIfHasDeeplink(drawId: Int?) {
        navigatedDrawId = drawId?.toLong()
    }

    fun isNavigatedDrawIdChanged(newDrawId: Int?) = newDrawId?.toLong() != navigatedDrawId

    fun isUserLogged(): Boolean = authRepositoryImpl.isUserLogged()

    fun getActiveDraws() {
        makeNetworkRequest(
            requestFunc = { homeRepository.getActiveDraws() },
            onSuccess = { draws ->
                _drawListViewState.postValue(DrawListViewState(draws))
                setEvent(_isRefreshingStatus, false)
            },
            onError = {
                setEvent(_isRefreshingStatus, false)
            }
        )
    }
}
