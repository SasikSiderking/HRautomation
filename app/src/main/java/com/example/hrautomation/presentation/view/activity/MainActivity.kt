package com.example.hrautomation.presentation.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hrautomation.R
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as App).appComponent.inject(this)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)

        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.meetingRoomFragment, R.id.colleaguesFragment, R.id.productFragment, R.id.eatingFragment, R.id.faqFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

        this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(
        true // default to enabled
    ) {
        private var isBackPressedOnce: Boolean = false
        override fun handleOnBackPressed() {
            if (isBackPressedOnce) {
                finishAffinity()
            }
            Toast.makeText(this@MainActivity, "Нажмите еще раз чтобы выйти", Toast.LENGTH_SHORT).show()
            isBackPressedOnce = true

            lifecycleScope.launch(Dispatchers.IO) {
                delay(3000)
                isBackPressedOnce = false
            }
        }
    }
}