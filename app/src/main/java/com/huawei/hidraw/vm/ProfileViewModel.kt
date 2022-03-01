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
import com.huawei.hidraw.data.repository.ProfileRepository
import com.huawei.hidraw.ui.profile.UserInfoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : BaseViewModel() {

    private val _userInfoViewState = MutableLiveData(UserInfoViewState.initial())
    val userInfoViewState get() = _userInfoViewState

    init {
        getProfileDetail()
    }

    private fun getProfileDetail() {
        makeNetworkRequest(
            requestFunc = { profileRepository.getUser() },
            onSuccess = {
                _userInfoViewState.postValue(UserInfoViewState(it))
            }
        )
    }
}
