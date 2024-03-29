package com.example.hrautomation.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hrautomation.R
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityMainBinding
import com.example.hrautomation.presentation.view.loading.LoadingActivity
import com.example.hrautomation.presentation.view.profile.ProfileActivity
import com.example.hrautomation.presentation.view.social.filter.EventFilterActivity
import com.example.hrautomation.utils.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    private var isFilterMenuItemVisible = false

    private var filterMenuIconDrawable: Drawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as App).appComponent.inject(this)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)

        val appBarConfiguration =
            AppBarConfiguration(
                setOf(
                    R.id.colleaguesFragment,
                    R.id.productFragment,
                    R.id.faqFragment,
                    R.id.restaurantsFragment,
                    R.id.socialFragment
                )
            )
        setupActionBarWithNavController(navController, appBarConfiguration)

        this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        navController.addOnDestinationChangedListener(navListener)

        viewModel.filterMenuIconDrawableResId.observe(this, filterDrawableObserver)
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

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val filterMenuItem = menu!!.findItem(R.id.action_filter)
        if (filterMenuIconDrawable != null) {
            filterMenuItem.icon = filterMenuIconDrawable
        }
        filterMenuItem.isVisible = isFilterMenuItemVisible
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_action_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_profile -> openProfile()
            R.id.action_exit -> logout()
            R.id.action_filter -> openEventFilter()
        }

        return super.onOptionsItemSelected(item)
    }

    private val navListener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
        isFilterMenuItemVisible = destination.id == R.id.socialFragment
        invalidateOptionsMenu()
    }

    private val filterDrawableObserver = Observer<Int?> { drawableResId ->
        filterMenuIconDrawable = ContextCompat.getDrawable(this, drawableResId)
        invalidateOptionsMenu()
    }

    private fun openEventFilter() {
        eventFilterActivityResultLauncher.launch(EventFilterActivity.createIntent(this))
    }

    private fun logout() {
        viewModel.logout()
        val intent = LoadingActivity.createIntent(this)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun openProfile() {
        profileActivityResultLauncher.launch(ProfileActivity.createIntent(this))
    }

    private fun setFilterIconType(isFilterActive: Boolean) {
        if (isFilterActive) {
            viewModel.updateFilterIcon(R.drawable.ic_filter_active)
        } else {
            viewModel.updateFilterIcon(R.drawable.ic_filter_none)
        }
    }

    private var profileActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {}

    private var eventFilterActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        val isFilterActive = result.data?.getBooleanExtra(EventFilterActivity.RESULT_KEY, false)
        if (isFilterActive != null) {
            setFilterIconType(isFilterActive)
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}