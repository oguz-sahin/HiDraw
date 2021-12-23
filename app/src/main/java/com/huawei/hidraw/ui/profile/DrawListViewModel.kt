package com.huawei.hidraw.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.huawei.hidraw.R
import com.huawei.hidraw.core.BaseViewModel
import com.huawei.hidraw.data.model.DrawModel
import com.huawei.hidraw.data.repository.ProfileRepositoryImpl
import com.huawei.hidraw.ui.profile.DrawListTypes.USER_ATTENDED
import com.huawei.hidraw.ui.profile.DrawListTypes.USER_CREATED
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 12/13/2021.
 */
@HiltViewModel
class DrawListViewModel @Inject constructor(
    private val profileRepositoryImpl: ProfileRepositoryImpl
) : BaseViewModel() {

    private val _draws = MutableLiveData<List<DrawModel>>()
    val draws: LiveData<List<DrawModel>> get() = _draws

    fun getDrawsByType(drawListTypes: DrawListTypes?) {
        when (drawListTypes) {
            USER_ATTENDED -> getAttendedDrawsOfUser()
            USER_CREATED -> getCreatedDrawsByUser()
            null -> showErrorWithId(R.string.general_exception_message)
        }
    }

    private fun getAttendedDrawsOfUser() {
        viewModelScope.launch {
            makeNetworkRequest(
                requestFunc = {
                    profileRepositoryImpl.getAttendedDrawsOfUser()
                },
                onSuccess = {
                    _draws.postValue(it)
                }
            )
        }
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
