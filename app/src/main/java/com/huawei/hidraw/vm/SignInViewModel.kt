package com.huawei.hidraw.vm

import android.app.Activity
import androidx.activity.result.ActivityResult
import androidx.lifecycle.viewModelScope
import com.huawei.hidraw.core.BaseViewModel
import com.huawei.hidraw.data.repository.AuthRepository
import com.huawei.hidraw.ui.signin.SignInFragmentDirections.actionSignInFragmentToHomeFragment
import com.huawei.hms.common.ApiException
import com.huawei.hms.support.account.AccountAuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {


    init {
        if (isUserLogged()) {
            navigate(actionSignInFragmentToHomeFragment())
        }
    }


    fun signIn(result: ActivityResult) {
        viewModelScope.launch {
            if (result.resultCode == Activity.RESULT_OK) {
                val authAccountTask = AccountAuthManager.parseAuthResultFromIntent(result.data)
                if (authAccountTask.isSuccessful) {
                    // The sign-in is successful, and the user's ID information and ID token are obtained.
                    val authAccount = authAccountTask.result
                } else {
                    // The sign-in failed. No processing is required. Logs are recorded for fault locating.
                    showError("sign in failed : " + (authAccountTask.exception as ApiException).statusCode)
                }
            } else {
                showGeneralError()
            }
            showLoading()
        }
    }


    private fun isUserLogged(): Boolean = authRepository.isUserLogged()


}