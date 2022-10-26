package com.example.hrautomation.presentation.view.loading.activity_load

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityLoadingBinding
import com.example.hrautomation.presentation.view.activity.MainActivity
import com.example.hrautomation.utils.ViewModelFactory
import javax.inject.Inject

class LoadingActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: LoadingActivityViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
            .apply {
                setKeepOnScreenCondition {
                    viewModel.isLoading.value == true
                }
            }
        super.onCreate(savedInstanceState)

        (applicationContext as App).appComponent.inject(this)

        val binding = ActivityLoadingBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        initUi()
    }

    private val tokenObserver = Observer<Boolean> {
        if (it) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initUi() {
        viewModel.isTokenExist.observe(this, tokenObserver)
    }
}