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

package com.huawei.hidraw.core

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.huawei.hidraw.data.ResultWrapper
import com.huawei.hidraw.data.model.ErrorResponseModel
import com.huawei.hidraw.data.model.HttpException
import com.huawei.hidraw.data.model.IException
import com.huawei.hidraw.util.Event
import kotlinx.coroutines.launch

/**
 * Created by Oguz Sahin on 10/26/2021.
 */
abstract class BaseViewModel : ViewModel() {

    /** Internal loading state of ViewModel. Visual representation may differ. **/
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    /** Used for sending one-time signal to observers. **/
    private val _baseEvent = MutableLiveData<Event<BaseViewEvent>>()
    val baseEvent: LiveData<Event<BaseViewEvent>> = _baseEvent

    /** An helper function for sending one-time events to [baseEvent] **/
    private fun sendEvent(event: BaseViewEvent) = setEvent(_baseEvent, event)

    fun showLoading() {
        _loading.value = true
    }

    fun hideLoading() {
        _loading.value = false
    }

    /** Navigate to specific direction **/
    fun navigate(directions: NavDirections) = sendEvent(BaseViewEvent.NavigateTo(directions))

    @Suppress("MemberVisibilityCanBePrivate")
    fun showErrorWithId(@StringRes message: Int) = sendEvent(BaseViewEvent.ShowErrorWithId(message))

    fun showError(message: String) = sendEvent(BaseViewEvent.ShowError(message))

    fun showSuccess(message: String) = sendEvent(BaseViewEvent.ShowSuccess(message))

    fun showSuccess(@StringRes message: Int) = sendEvent(BaseViewEvent.ShowSuccessWithId(message))

    fun navigateBack() = sendEvent(BaseViewEvent.NavigateBack)

    fun <T> makeNetworkRequest(
        requestFunc: suspend () -> ResultWrapper<T>,
        onSuccess: ((value: T) -> Unit)? = null,
        onError: (() -> Unit)? = null
    ) {
        viewModelScope.launch {
            showLoading()
            when (val response = requestFunc.invoke()) {
                is ResultWrapper.Error -> {
                    onError?.invoke()
                    hideLoading()
                    handleError(response.value)
                }
                is ResultWrapper.Success -> {
                    hideLoading()
                    onSuccess?.invoke(response.value)
                }
            }
        }
    }

    private fun handleError(exception: IException) {
        when (exception) {
            is HttpException -> {
                val errorMessage = getErrorMessage(exception.errorResponseModel)
                showError(errorMessage)
            }
            else -> {
                showErrorWithId(exception.getMessage())
            }
        }
    }

    private fun getErrorMessage(errorResponseModel: ErrorResponseModel?): String =
        errorResponseModel?.result?.msg ?: ""

    fun <T> setEvent(mutableLiveData: MutableLiveData<Event<T>>, value: T) {
        mutableLiveData.value = Event(value)
    }
}
