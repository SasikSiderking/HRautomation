package com.example.hrautomation.presentation.view.loading.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityLoadingBinding
import com.example.hrautomation.utils.ViewModelFactory
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (applicationContext as App).appComponent.inject(this)

        val binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}