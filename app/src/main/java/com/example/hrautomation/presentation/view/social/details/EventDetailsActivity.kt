package com.example.hrautomation.presentation.view.social.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityEventDetailsBinding
import com.example.hrautomation.presentation.base.activity.BaseActivity
import com.example.hrautomation.utils.ui.switcher.ContentLoadingSettings
import com.example.hrautomation.utils.ui.switcher.ContentLoadingState

class EventDetailsActivity : BaseActivity<ActivityEventDetailsBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityEventDetailsBinding
        get() = { ActivityEventDetailsBinding.inflate(layoutInflater) }

    override fun initInject() {
        (applicationContext as App).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding.toolbar.title = "Соревы по доте"
    }

    override fun initUI() {
        with(binding) {

            contentLoadingSwitcher.setup(
                ContentLoadingSettings(
                    contentViews = listOf(
                        coordinatorLayout
                    ),
                    errorViews = listOf(reusableReload.reusableReload),
                    loadingViews = listOf(reusableLoading.progressBar),
                    initState = ContentLoadingState.CONTENT
                )
            )
        }
    }

    override fun initObserves() = Unit

    override fun initListeners() = Unit

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, EventDetailsActivity::class.java)
        }
    }
}