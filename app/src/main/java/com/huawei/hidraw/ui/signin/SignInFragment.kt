package com.huawei.hidraw.ui.signin

import androidx.fragment.app.viewModels
import com.huawei.hidraw.core.BaseFragmentWithViewModel
import com.huawei.hidraw.databinding.FragmentSignInBinding
import com.huawei.hidraw.util.ext.getStartActivityForResultLauncher
import com.huawei.hidraw.vm.SignInViewModel
import com.huawei.hms.support.account.service.AccountAuthService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : BaseFragmentWithViewModel<FragmentSignInBinding, SignInViewModel>(
    inflate = FragmentSignInBinding::inflate
) {
    override val viewModel: SignInViewModel by viewModels()

    private val signInResultLauncher = getStartActivityForResultLauncher { result ->
        viewModel.signIn(result)
    }

    @Inject
    lateinit var accountAuthService: AccountAuthService

    override fun initListener() {
        binding.btnSignIn.setOnClickListener {
            signInHuaweiId()
        }
    }

    private fun signInHuaweiId() {
        signInResultLauncher.launch(accountAuthService.signInIntent)
    }

}
