package com.example.hrautomation.presentation.view.social.filter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View.OnTouchListener
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
        binding.dateInputText.showSoftInputOnFocus = false
        with(binding) {
            contentLoadingSwitcher.setup(
                ContentLoadingSettings(
                    contentViews = listOf(nameInputLayout, dateInputLayout, cityInputLayout, formatInputLayout),
                    errorViews = listOf(reusableReload.reusableReload),
                    loadingViews = listOf(reusableLoading.progressBar),
                    initState = ContentLoadingState.CONTENT
                )
            )
        }
    }

    override fun initObserves() = Unit

    override fun initListeners() {
        binding.dateInputText.setOnTouchListener(pickDate)

        supportFragmentManager.setFragmentResultListener(
            DatePickerFragment.REQUEST_KEY,
            this
        ) { _: String, bundle: Bundle ->
            val datePickerDialogResult = bundle.getSerializable(DatePickerFragment.RESULT_KEY) as DatePickerDialogResult?
            if (datePickerDialogResult != null) {
                with(datePickerDialogResult) {
                    val localDate = DateUtils.formatDate(
                        LocalDate(
                            year,
                            month,
                            day
                        ).toDate()
                    )
                    binding.dateInputText.setText(localDate)
                    viewModel.setDateFilter(localDate)
                }
            }
            binding.dateInputText.clearFocus()
        }

        binding.acceptButton.setOnClickListener {
            viewModel.setNameFilter(binding.nameInputText.text.toString())
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

    private val pickDate = OnTouchListener { view, motionEvent ->
        if (motionEvent.action == MotionEvent.ACTION_UP) {
            view.performClick()
            val newFragment = DatePickerFragment.newInstance()
            view.requestFocus()
            newFragment.show(supportFragmentManager, DatePickerFragment.TAG)
            return@OnTouchListener true
        }
        return@OnTouchListener false
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, EventFilterActivity::class.java)
        }
    }
}