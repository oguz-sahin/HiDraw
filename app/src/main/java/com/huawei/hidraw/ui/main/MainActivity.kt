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

package com.huawei.hidraw.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.huawei.hidraw.R
import com.huawei.hidraw.databinding.ActivityMainBinding
import com.huawei.hidraw.util.ext.executeWithAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val navController by lazy { (supportFragmentManager.findFragmentById(R.id.navHostFragmentContainer) as NavHostFragment).navController }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_HiDraw)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        setViewState(MainActivityViewState(isFabVisible = false, isBottomNavigationVisible = false))
        setupNavigation()
        setNavDestinationListener()
        setupFabClickListener()
    }

    private fun setupNavigation() {
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun setNavDestinationListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.signInFragment, R.id.createDrawFragment, R.id.drawResultFragment -> {
                    setViewState(
                        MainActivityViewState(
                            isFabVisible = false,
                            isBottomNavigationVisible = false
                        )
                    )
                }
                else -> {
                    setViewState(
                        MainActivityViewState(
                            isFabVisible = true,
                            isBottomNavigationVisible = true
                        )
                    )
                }
            }
        }
    }

    private fun setupFabClickListener() {
        binding.fab.setOnClickListener {
            navController.navigate(R.id.action_global_createDrawFragment)
            binding.bottomNavigationView.selectedItemId = R.id.menu_invisible
        }
    }

    private fun setViewState(mainActivityViewState: MainActivityViewState) {
        binding.executeWithAction {
            viewState = mainActivityViewState
        }
    }
}
