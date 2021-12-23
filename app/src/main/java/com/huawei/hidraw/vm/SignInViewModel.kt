package com.huawei.hidraw.vm

import androidx.activity.result.ActivityResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.huawei.hidraw.R
import com.huawei.hidraw.core.BaseViewModel
import com.huawei.hidraw.data.model.UserModel
import com.huawei.hidraw.data.repository.AuthRepository
import com.huawei.hidraw.ui.signin.SignInFragmentDirections.actionSignInFragmentToHomeFragment
import com.huawei.hidraw.ui.signin.SignInViewEvent
import com.huawei.hidraw.ui.signin.SignInViewEvent.SignInWithHuaweiId
import com.huawei.hidraw.util.Event
import com.huawei.hms.support.account.AccountAuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {

    private val _viewEvent = MutableLiveData<Event<SignInViewEvent>>()
    val viewEvent: LiveData<Event<SignInViewEvent>> get() = _viewEvent

    init {
        if (isUserLogged()) {
            navigate(actionSignInFragmentToHomeFragment())
        }
    }

    fun signIn(result: ActivityResult, instagramUserName: String) {
        viewModelScope.launch {
            val authAccountTask = AccountAuthManager.parseAuthResultFromIntent(result.data)
            if (authAccountTask.isSuccessful) {
                // The sign-in is successful, and the user's ID information and ID token are obtained.
                val userModel = authAccountTask.result.let {
                    UserModel(
                        email = it.email ?: "",
                        name = it.displayName ?: "",
                        userId = it.unionId,
                        igUserName = instagramUserName
                    )
                }
                register(userModel)
            }
        }
    }

    fun checkInstagramUserNameAndSignIn(instagramUserName: String) {
        if (instagramUserName.isNotEmpty()) {
            setEvent(mutableLiveData = _viewEvent, value = SignInWithHuaweiId)
        } else {
            showErrorWithId(R.string.empty_username_error)
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
