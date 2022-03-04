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

package com.hms.hidraw.ui.signin

import androidx.fragment.app.viewModels
import com.hms.hidraw.core.BaseFragmentWithViewModel
import com.hms.hidraw.databinding.FragmentSignInBinding
import com.hms.hidraw.util.ext.getStartActivityForResultLauncher
import com.hms.hidraw.vm.SignInViewModel
import com.huawei.hms.support.account.service.AccountAuthService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : BaseFragmentWithViewModel<FragmentSignInBinding, SignInViewModel>(
    inflate = FragmentSignInBinding::inflate
) {
    override val viewModel: SignInViewModel by viewModels()

    private val signInResultLauncher = getStartActivityForResultLauncher { result ->
        viewModel.signIn(result, requireContext())
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
