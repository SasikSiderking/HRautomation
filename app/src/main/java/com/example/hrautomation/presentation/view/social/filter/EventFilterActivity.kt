package com.example.hrautomation.presentation.view.social.filter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View.OnClickListener
import androidx.activity.viewModels
import com.example.hrautomation.R
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.filter_events)
    }

    override fun initUI() {
        with(binding) {
            contentLoadingSwitcher.setup(
                ContentLoadingSettings(
                    contentViews = listOf(dateLabel, pickedDate, cityLabel, pickedCity, formatLabel, pickedFormat),
                    errorViews = listOf(reusableReload.reusableReload),
                    loadingViews = listOf(reusableLoading.progressBar),
                    initState = ContentLoadingState.CONTENT
                )
            )
            reusableReload.reloadButton.setOnClickListener {
                viewModel.reload()
                contentLoadingSwitcher.switchState(ContentLoadingState.LOADING, SwitchAnimationParams(delay = 500L))
            }
        }
    }

    override fun initObserves() = Unit

    override fun initListeners() {
        binding.pickedDate.setOnClickListener(pickDate)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private val pickDate = OnClickListener {
        val newFragment = DatePickerFragment.newInstance()
        newFragment.show(supportFragmentManager, DatePickerFragment.TAG)
    }

    companion object {


        fun createIntent(context: Context): Intent {
            return Intent(context, EventFilterActivity::class.java)
        }
    }
}