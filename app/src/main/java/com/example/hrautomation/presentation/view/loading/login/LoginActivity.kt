package com.example.hrautomation.presentation.view.loading.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.hrautomation.R
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityLoadingBinding
import com.example.hrautomation.presentation.base.activity.BaseActivity

class LoginActivity : BaseActivity<ActivityLoadingBinding>() {

    lateinit var navController: NavController

    override val bindingInflater: (LayoutInflater) -> ActivityLoadingBinding
        get() = { ActivityLoadingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_loading_fragment) as NavHostFragment

        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener(onDestinationChangedListener)
    }

    override fun initInject() {
        (applicationContext as App).appComponent.inject(this)
    }

    override fun initUI() = Unit

    override fun initObserves() = Unit

    override fun initListeners() = Unit

    private val onDestinationChangedListener = NavController.OnDestinationChangedListener { _, destination, _ ->
        when (destination.id) {
            R.id.emailLogin -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }

            R.id.codeLogin -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navController.navigateUp()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}