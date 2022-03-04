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

package com.hms.hidraw.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.hms.hidraw.R
import com.hms.hidraw.core.BaseFragment.SnackBarType.ERROR
import com.hms.hidraw.core.BaseFragment.SnackBarType.SUCCESS

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    open fun initListener() {} // NOSONAR

    open fun initObserver() {} // NOSONAR

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initListener()
    }

    fun showSuccess(@StringRes message: Int) = showMessage(SUCCESS, getString(message))

    fun showSuccess(message: String) = showMessage(SUCCESS, message)

    fun showError(@StringRes message: Int) = showMessage(ERROR, getString(message))

    fun showError(message: String) = showMessage(ERROR, message)

    fun navigateDirections(directions: NavDirections) {
        findNavController().navigate(directions)
    }

    fun navigateBack() {
        findNavController().popBackStack()
    }

    private fun showMessage(type: SnackBarType, message: String) {
        val backgroundColor = when (type) {
            SUCCESS -> R.color.green
            ERROR -> R.color.red
        }
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            .setBackgroundTint(ContextCompat.getColor(requireContext(), backgroundColor))
            .show()
    }

    fun showMessageWithAction(
        @StringRes message: Int,
        @StringRes actionMessage: Int,
        action: () -> Unit
    ) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red))
            .setAction(actionMessage) {
                action.invoke()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    enum class SnackBarType {
        SUCCESS, ERROR
    }
}
