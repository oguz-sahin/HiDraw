package com.huawei.hidraw.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.huawei.hidraw.R
import com.huawei.hidraw.databinding.ActivityMainBinding
import com.huawei.hidraw.util.ext.hide
import com.huawei.hidraw.util.ext.show
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

        setupNavigation()
        setNavDestinationListener()
    }


    private fun setupNavigation() {
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun setNavDestinationListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.signInFragment -> {
                    binding.bottomNavigationView.hide()
                }
                else -> binding.bottomNavigationView.show()
            }
        }
    }

}