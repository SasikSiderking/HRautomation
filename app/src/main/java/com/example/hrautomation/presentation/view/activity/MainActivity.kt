package com.example.hrautomation.presentation.view.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.hrautomation.R
import com.example.hrautomation.app.App
import com.example.hrautomation.data.repository.UserRepository
import com.example.hrautomation.databinding.ActivityMainBinding
import com.example.hrautomation.di.AppComponent
import com.example.hrautomation.domain.repository.IUserRepository
import com.google.android.material.navigation.NavigationView
import javax.inject.Inject

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.lifecycleOwner = this

        binding.toolbar.setNavigationOnClickListener{
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)

        Log.i("test",(userRepository.getToken() as IUserRepository.GetTokenEvent.Token).token)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }
}