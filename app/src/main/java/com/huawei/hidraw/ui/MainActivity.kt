package com.huawei.hidraw.ui

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.huawei.hidraw.R
import com.huawei.hidraw.databinding.ActivityMainBinding
import com.huawei.hidraw.util.ext.hide
import com.huawei.hidraw.util.ext.show
import com.huawei.hidraw.vm.HomeViewModel
import com.huawei.hidraw.vm.MainActivityViewModel
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


        setViewState(MainActivityViewState())
        setupNavigation()
        setNavDestinationListener()
        setupNavItemClickListener()
        setupFabClickListener()
    }

    private fun setupNavigation() {
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun setupNavItemClickListener() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    navController.navigate(R.id.action_global_homeFragment)
                }
                R.id.menu_profile -> {
                    navController.navigate(R.id.action_global_profileFragment)
                }
            }
            binding.fab.show()
            true
        }
    }

    private fun setNavDestinationListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.signInFragment, R.id.createDrawFragment -> {
                    setViewState(
                        MainActivityViewState(
                        isFabVisible = false,
                        isBottomNavigationVisible = false
                    ))
                }
                else -> {
                    setViewState(
                        MainActivityViewState(
                            isFabVisible = true,
                            isBottomNavigationVisible = true
                        ))
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

    private fun setViewState(viewState: MainActivityViewState){
        binding.viewState = viewState
        binding.executePendingBindings()
    }

}