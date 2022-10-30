package com.example.hrautomation.presentation.view.loading

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.hrautomation.app.App
import com.example.hrautomation.presentation.view.activity.MainActivity
import com.example.hrautomation.presentation.view.loading.activity_login.LoginActivity
import com.example.hrautomation.utils.ViewModelFactory
import javax.inject.Inject

class LoadingActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: LoadingActivityViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {

//        installSplashScreen().apply {
//            setKeepOnScreenCondition{viewModel.isLoading.value == true}
//        }

        super.onCreate(savedInstanceState)
        (applicationContext as App).appComponent.inject(this)

        viewModel.isTokenExist.observe(this, tokenObserver)
    }


    private val tokenObserver = Observer<Boolean> { isTokenExist ->
        if (isTokenExist) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        finish()
    }
}