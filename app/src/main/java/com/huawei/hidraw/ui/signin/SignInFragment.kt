package com.huawei.hidraw.ui.signin

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.huawei.hidraw.core.BaseFragmentWithViewModel
import com.huawei.hidraw.databinding.FragmentSignInBinding
import com.huawei.hidraw.ui.signin.SignInViewEvent.SignInWithHuaweiId
import com.huawei.hidraw.util.ext.getContent
import com.huawei.hidraw.util.ext.observeEvent
import com.huawei.hidraw.vm.SignInViewModel
import com.huawei.hms.support.account.service.AccountAuthService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : BaseFragmentWithViewModel<FragmentSignInBinding, SignInViewModel>(
    inflate = FragmentSignInBinding::inflate
) {
    override val viewModel: SignInViewModel by viewModels()

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private val instagramUserName
        get() = binding.etInstagramUserName.getContent()

    @Inject
    lateinit var accountAuthService: AccountAuthService

    override fun initListener() {
        binding.btnSignIn.setOnClickListener {
            viewModel.checkInstagramUserNameAndSignIn(instagramUserName)
        }
    }

    override fun initObserver() {
        observeEvent(viewModel.viewEvent, ::handleViewEvent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResultLauncher()
    }

    private fun handleViewEvent(signInViewEvent: SignInViewEvent) {
        when (signInViewEvent) {
            SignInWithHuaweiId -> signInHuaweiId()
        }
    }

    private fun setResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                viewModel.signIn(result, instagramUserName)
            }
    }


    private fun signInHuaweiId() {
        resultLauncher.launch(accountAuthService.signInIntent)
    }
}