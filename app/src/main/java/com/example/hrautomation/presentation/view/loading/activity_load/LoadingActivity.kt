package com.example.hrautomation.presentation.view.loading.activity_load

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.hrautomation.R
import com.example.hrautomation.databinding.ActivityLoadingBinding
import com.example.hrautomation.presentation.view.activity.MainActivity
import com.example.hrautomation.presentation.view.activity.appComponent
import com.example.hrautomation.utils.ViewModelFactory
import javax.inject.Inject

class LoadingActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel: LoadingActivityViewModel by viewModels{
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

        appComponent.inject(this)

        val binding: ActivityLoadingBinding = DataBindingUtil.setContentView(this, R.layout.activity_loading)
        binding.lifecycleOwner = this

        viewModel.isTokenExist.observe(this,tokenObserver)
    }

    private val tokenObserver = Observer<Boolean>{
        if(it){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}