package com.huawei.hidraw.vm

import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.lifecycle.viewModelScope
import com.huawei.hidraw.R
import com.huawei.hidraw.core.BaseViewModel
import com.huawei.hidraw.data.model.PushTokenBodyModel
import com.huawei.hidraw.data.model.UserModel
import com.huawei.hidraw.data.repository.AuthRepository
import com.huawei.hidraw.di.IoDispatcher
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


    init {
        if (isUserLogged()) {
            navigate(actionSignInFragmentToHomeFragment())
        }
    }

    fun signIn(result: ActivityResult) {
        viewModelScope.launch {
            val authAccountTask = AccountAuthManager.parseAuthResultFromIntent(result.data)

            if (authAccountTask.isSuccessful) {
                // The sign-in is successful, and the user's ID information and ID token are obtained.
                val userModel = authAccountTask.result.let {
                    UserModel(
                        email = it.email ?: "",
                        name = it.displayName ?: "",
                        userId = it.unionId
                    )
                }
                register(userModel)
            }
        }
    }


    private fun isUserLogged(): Boolean = authRepositoryImpl.isUserLogged()

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
