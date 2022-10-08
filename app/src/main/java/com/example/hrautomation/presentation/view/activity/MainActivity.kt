package com.example.hrautomation.presentation.view.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.hrautomation.R
import com.example.hrautomation.app.App
import com.example.hrautomation.di.AppComponent
import com.google.android.material.navigation.NavigationView
import javax.inject.Inject

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle

    @Inject
    lateinit var message: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appComponent.inject(this)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        toolBar.setNavigationOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }
}