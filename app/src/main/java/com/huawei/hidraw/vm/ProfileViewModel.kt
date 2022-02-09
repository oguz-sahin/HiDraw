package com.huawei.hidraw.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.huawei.hidraw.core.BaseViewModel
import com.huawei.hidraw.data.repository.ProfileRepository
import com.huawei.hidraw.ui.profile.UserInfoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 11/23/2021.
 */

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : BaseViewModel() {

    private val _userInfoViewState = MutableLiveData(UserInfoViewState.initial())
    val userInfoViewState get() = _userInfoViewState


    fun getProfileDetail() {
        viewModelScope.launch {
            makeNetworkRequest(
                requestFunc = {
                    profileRepository.getUser()
                },
                onSuccess = {
                    _userInfoViewState.postValue(UserInfoViewState(it))
                }
            )
        }
    }
}
