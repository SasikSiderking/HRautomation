package com.example.hrautomation.presentation.view.loading

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.hrautomation.app.App
import com.example.hrautomation.presentation.view.activity.MainActivity
import com.example.hrautomation.presentation.view.loading.login.LoginActivity
import com.example.hrautomation.utils.ViewModelFactory
import javax.inject.Inject

class LoadingActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: LoadingActivityViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        (applicationContext as App).appComponent.inject(this)

        viewModel.isTokenExist.observe(this, tokenObserver)
    }


    private val tokenObserver = Observer<Boolean> { isTokenExist ->
        if (isTokenExist) {
            val intent = MainActivity.createIntent(this)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } else {
            val intent = LoginActivity.createIntent(this)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        finish()
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, LoadingActivity::class.java)
        }
    }
}