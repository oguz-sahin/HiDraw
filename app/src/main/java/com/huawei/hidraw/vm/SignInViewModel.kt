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

import android.content.Context
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.lifecycle.viewModelScope
import com.huawei.hidraw.R
import com.huawei.hidraw.core.BaseViewModel
import com.huawei.hidraw.data.model.PushTokenBodyModel
import com.huawei.hidraw.data.model.UserModel
import com.huawei.hidraw.data.repository.AuthRepository
import com.huawei.hidraw.di.IoDispatcher
import com.huawei.hidraw.network.NetworkUtils
import com.huawei.hidraw.ui.signin.SignInFragmentDirections.actionSignInFragmentToHomeFragment
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.common.ApiException
import com.huawei.hms.support.account.AccountAuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepository,
    private val hmsInstanceId: HmsInstanceId,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    fun signIn(result: ActivityResult, context: Context) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            showErrorWithId(R.string.no_connection_exception_message)
            return
        }
        viewModelScope.launch {
            val authAccountTask = AccountAuthManager.parseAuthResultFromIntent(result.data)
            if (authAccountTask.isSuccessful.not()) {
                showError(authAccountTask.exception.localizedMessage ?: "")
                return@launch
            }

            // The sign-in is successful, and the user's ID information and ID token are obtained.
            val userModel = with(authAccountTask.result) {
                UserModel(
                    email = email ?: "",
                    name = displayName ?: "",
                    userId = unionId
                )
            }

            register(userModel)
        }
    }

    private fun register(userModel: UserModel) {
        makeNetworkRequest(
            requestFunc = { authRepositoryImpl.register(userModel) },
            onSuccess = {
                authRepositoryImpl.saveUserId(it.userId)
                sendPushTokenToServer()
            }
        )
    }

    private fun sendPushTokenToServer() {
        viewModelScope.launch {
            val pushToken = getPushToken()
            pushToken?.let {
                val pushTokenBodyModel = PushTokenBodyModel(pushToken = pushToken)
                makeNetworkRequest(
                    requestFunc = {
                        authRepositoryImpl.sendPushTokenToServer(pushTokenBodyModel = pushTokenBodyModel)
                    },
                    onSuccess = {
                        showSuccess(R.string.sign_in_successfully)
                        navigate(actionSignInFragmentToHomeFragment())
                    }
                )
            }
        }
    }

    private suspend fun getPushToken(): String? {
        return withContext(ioDispatcher) {
            try {
                hmsInstanceId.getToken(APP_ID_FROM_AGC, TOKEN_SCOPE)
            } catch (e: ApiException) {
                Log.e("PushToken", "get token failed, $e")
                null
            }
        }
    }

    companion object {
        const val APP_ID_FROM_AGC = "104877425"
        const val TOKEN_SCOPE = "HCM"
    }
}
