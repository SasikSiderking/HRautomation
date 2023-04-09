package com.example.hrautomation.presentation.view.social.filter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View.OnClickListener
import androidx.activity.viewModels
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.FragmentEventFilterBinding
import com.example.hrautomation.presentation.base.activity.BaseActivity
import com.example.hrautomation.presentation.view.social.SocialViewModel
import com.example.hrautomation.utils.ui.switcher.ContentLoadingSettings
import com.example.hrautomation.utils.ui.switcher.ContentLoadingState
import com.example.hrautomation.utils.ui.switcher.base.SwitchAnimationParams

class EventFilterActivity : BaseActivity<FragmentEventFilterBinding>() {
    override val bindingInflater: (LayoutInflater) -> FragmentEventFilterBinding
        get() = { FragmentEventFilterBinding.inflate(layoutInflater) }

    private val viewModel: SocialViewModel by viewModels {
        viewModelFactory
    }

    override fun initInject() {
        (applicationContext as App).appComponent.inject(this)
    }

    override fun initUI() {
        with(binding) {
            contentLoadingSwitcher.setup(
                ContentLoadingSettings(
                    contentViews = listOf(),
                    errorViews = listOf(reusableReload.reusableReload),
                    loadingViews = listOf(reusableLoading.progressBar),
                    initState = ContentLoadingState.LOADING
                )
            )
            reusableReload.reloadButton.setOnClickListener {
                viewModel.reload()
                contentLoadingSwitcher.switchState(ContentLoadingState.LOADING, SwitchAnimationParams(delay = 500L))
            }
        }
    }

    override fun initObserves() {
    }

    override fun initListeners() {
        binding.pickedDate.setOnClickListener(pickDate)
    }

    private val pickDate = OnClickListener {
        val newFragment = DatePickerFragment()
        newFragment.show(supportFragmentManager, "datePicker")
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, EventFilterActivity::class.java)
        }
    }
}