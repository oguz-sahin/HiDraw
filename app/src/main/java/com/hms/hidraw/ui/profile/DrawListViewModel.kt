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

package com.hms.hidraw.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hms.hidraw.R
import com.hms.hidraw.core.BaseViewModel
import com.hms.hidraw.data.model.DrawModel
import com.hms.hidraw.data.repository.ProfileRepositoryImpl
import com.hms.hidraw.ui.profile.DrawListTypes.USER_ATTENDED
import com.hms.hidraw.ui.profile.DrawListTypes.USER_CREATED
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawListViewModel @Inject constructor(
    private val profileRepositoryImpl: ProfileRepositoryImpl,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _draws = MutableLiveData<List<DrawModel>>()
    val draws: LiveData<List<DrawModel>> get() = _draws

    init {
        val drawListArgs = savedStateHandle.get<String>(DrawListFragment.DRAW_LIST_TYPE)
        val drawListType = DrawListTypes.valueOf(drawListArgs ?: "")
        getDrawsByType(drawListType)
    }

    private fun getDrawsByType(drawListTypes: DrawListTypes?) {
        when (drawListTypes) {
            USER_ATTENDED -> getAttendedDrawsOfUser()
            USER_CREATED -> getCreatedDrawsByUser()
            null -> showErrorWithId(R.string.general_exception_message)
        }
    }

    private fun getAttendedDrawsOfUser() {
        makeNetworkRequest(
            requestFunc = {
                profileRepositoryImpl.getAttendedDrawsOfUser()
            },
            onSuccess = {
                _draws.postValue(it)
            }
        )
    }

    private fun getCreatedDrawsByUser() {
        viewModelScope.launch {
            makeNetworkRequest(
                requestFunc = {
                    profileRepositoryImpl.getCreatedDrawsByUser()
                },
                onSuccess = {
                    _draws.postValue(it)
                }
            )
        }
    }
}
