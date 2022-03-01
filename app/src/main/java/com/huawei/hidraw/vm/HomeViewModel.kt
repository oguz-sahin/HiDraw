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

import androidx.lifecycle.MutableLiveData
import com.huawei.hidraw.core.BaseViewModel
import com.huawei.hidraw.data.model.DrawModel
import com.huawei.hidraw.data.repository.AuthRepository
import com.huawei.hidraw.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 11/23/2021.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val authRepositoryImpl: AuthRepository
) : BaseViewModel() {

    private var navigatedDrawId: Long? = null

    private val _activeDraws = MutableLiveData<List<DrawModel>>()
    val activeDraws get() = _activeDraws

    fun setDrawIdIfHasDeeplink(drawId: Int?) {
        navigatedDrawId = drawId?.toLong()
    }

    fun isNavigatedDrawIdChanged(newDrawId: Int?) = newDrawId?.toLong() != navigatedDrawId

    fun isUserLogged(): Boolean = authRepositoryImpl.isUserLogged()

    fun getActiveDraws() {
        makeNetworkRequest(
            requestFunc = { homeRepository.getActiveDraws() },
            onSuccess = { draws ->
                _activeDraws.postValue(draws)
            }
        )
    }
}
