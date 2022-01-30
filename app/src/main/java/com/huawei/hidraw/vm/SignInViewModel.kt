package com.huawei.hidraw.vm

import androidx.activity.result.ActivityResult
import androidx.lifecycle.viewModelScope
import com.huawei.hidraw.R
import com.huawei.hidraw.core.BaseViewModel
import com.huawei.hidraw.data.model.UserModel
import com.huawei.hidraw.data.repository.AuthRepository
import com.huawei.hidraw.ui.signin.SignInFragmentDirections.actionSignInFragmentToHomeFragment
import com.huawei.hms.support.account.AccountAuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
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


    private fun isUserLogged(): Boolean = authRepository.isUserLogged()

    private fun register(userModel: UserModel) {
        makeNetworkRequest(
            requestFunc = { authRepository.register(userModel) },
            onSuccess = {
                showSuccess(R.string.sign_in_successfully)
                authRepository.saveUserId(it.userId)
                navigate(actionSignInFragmentToHomeFragment())
            }
        )
    }
}
