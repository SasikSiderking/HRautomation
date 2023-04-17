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
import com.example.hrautomation.databinding.ActivityEventFilterBinding
import com.example.hrautomation.presentation.base.activity.BaseActivity
import com.example.hrautomation.presentation.model.social.DatePickerDialogResult
import com.example.hrautomation.utils.date.DateUtils
import com.example.hrautomation.utils.ui.switcher.ContentLoadingSettings
import com.example.hrautomation.utils.ui.switcher.ContentLoadingState
import org.joda.time.LocalDate

class EventFilterActivity : BaseActivity<ActivityEventFilterBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityEventFilterBinding
        get() = { ActivityEventFilterBinding.inflate(layoutInflater) }

    private val viewModel: EventFilterViewModel by viewModels {
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
        }
    }

    override fun initObserves() = Unit

    override fun initListeners() {
        binding.pickedDate.setOnClickListener(pickDate)

        supportFragmentManager.setFragmentResultListener(
            DatePickerFragment.REQUEST_KEY,
            this
        ) { _: String, bundle: Bundle ->
            val datePickerDialogResult = bundle.getSerializable(DatePickerFragment.RESULT_KEY) as DatePickerDialogResult
            with(datePickerDialogResult) {
                val localDate = DateUtils.formatDate(
                    LocalDate(
                        year,
                        month,
                        day
                    ).toDate()
                )
                binding.pickedDate.text = localDate
                viewModel.setDateFilter(localDate)
            }
        }

        binding.acceptButton.setOnClickListener {
            viewModel.sendFilterParam()
            finish()
        }
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