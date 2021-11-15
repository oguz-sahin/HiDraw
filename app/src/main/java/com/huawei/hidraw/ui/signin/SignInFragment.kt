package com.huawei.hidraw.ui.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.huawei.hidraw.core.BaseFragmentWithViewModel
import com.huawei.hidraw.databinding.FragmentSignInBinding
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

    @Inject
    lateinit var accountAuthService: AccountAuthService


    override fun initListener() {
        super.initListener()
        binding.btnSignIn.setOnClickListener {
            signInHuaweiId()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResultLauncher()
    }

    private fun setResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                viewModel.signIn(result)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    private fun signInHuaweiId() {
        resultLauncher.launch(accountAuthService.signInIntent)
    }
}