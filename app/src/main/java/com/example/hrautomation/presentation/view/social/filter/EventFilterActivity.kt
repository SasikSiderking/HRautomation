package com.example.hrautomation.presentation.view.social.filter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.Observer
import com.example.hrautomation.R
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityEventFilterBinding
import com.example.hrautomation.presentation.base.activity.BaseActivity
import com.example.hrautomation.presentation.model.restaurants.CityItem
import com.example.hrautomation.presentation.model.social.filter.DatePickerDialogResult
import com.example.hrautomation.presentation.model.social.filter.EventFilterParam
import com.example.hrautomation.presentation.view.restaurants.сity.CityBottomSheet
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

    private var isFilterActive: Boolean = false

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
                    contentViews = listOf(
                        nameInputLayout,
                        fromDateInputLayout,
                        toDateInputLayout,
                        cityInputLayout,
                        formatInputLayout
                    ),
                    errorViews = listOf(reusableReload.reusableReload),
                    loadingViews = listOf(reusableLoading.progressBar),
                    initState = ContentLoadingState.CONTENT
                )
            )
        }
    }

    override fun initObserves() {
        viewModel.eventFilterParam.observe(this, eventFilterParamObserver)
    }

    override fun initListeners() {
        with(binding) {
            nameInputLayout.setEndIconOnClickListener {
                viewModel.setNameFilter(null)
                nameInputText.text?.clear()
            }
            fromDateInputLayout.setEndIconOnClickListener {
                viewModel.setFromDateFilter(null)
                fromDateInputText.text?.clear()
            }
            toDateInputLayout.setEndIconOnClickListener {
                viewModel.setToDateFilter(null)
                toDateInputText.text?.clear()
            }
            cityInputLayout.setEndIconOnClickListener {
                viewModel.setCityFilter(null)
                cityInputText.text?.clear()
            }


            fromDateInputText.setOnClickListener {
                val newFragment = DatePickerFragment.newInstance(DatePickerFragment.FROM_REQUEST)
                newFragment.show(supportFragmentManager, DatePickerFragment.TAG)
            }
            toDateInputText.setOnClickListener {
                val newFragment = DatePickerFragment.newInstance(DatePickerFragment.TO_REQUEST)
                newFragment.show(supportFragmentManager, DatePickerFragment.TAG)
            }
            cityInputText.setOnClickListener {
                val newFragment = CityBottomSheet.newInstance()
                newFragment.show(supportFragmentManager, CityBottomSheet.TAG)
            }

            val arrayAdapter =
                ArrayAdapter(
                    this@EventFilterActivity,
                    R.layout.event_format_dropdown_item,
                    EventFormat.values().map { getString(it.eventType) })
            formatInputText.setAdapter(arrayAdapter)

            formatInputText.setOnItemClickListener { _, _, position, _ ->
                val selectedFormat = EventFormat.values()[position]
                viewModel.setFormatFilter(selectedFormat)
            }


            acceptButton.setOnClickListener {
                viewModel.setNameFilter(binding.nameInputText.text.toString())
                viewModel.sendFilterParam()
                intent.putExtra(RESULT_KEY, isFilterActive)
                setResult(REQUEST_KEY, intent)
                finish()
            }
        }

        supportFragmentManager.setFragmentResultListener(
            DatePickerFragment.FROM_REQUEST,
            this,
            fromDatePickerResultListener
        )

        supportFragmentManager.setFragmentResultListener(
            DatePickerFragment.TO_REQUEST,
            this,
            toDatePickerResultListener
        )

        supportFragmentManager.setFragmentResultListener(
            CityBottomSheet.REQUEST_KEY,
            this,
            cityFragmentResultListener
        )
    }

    private val eventFilterParamObserver = Observer<EventFilterParam> { eventFilterParam ->
        isFilterActive = !eventFilterParam.name.isNullOrEmpty() ||
                eventFilterParam.fromDate != null ||
                eventFilterParam.toDate != null ||
                eventFilterParam.city != null ||
                eventFilterParam.format?.value != null
        with(binding) {
            if (!eventFilterParam.name.isNullOrEmpty()) {
                nameInputText.setText(eventFilterParam.name)
            }

            fromDateInputLayout.isEndIconVisible = eventFilterParam.fromDate != null
            toDateInputLayout.isEndIconVisible = eventFilterParam.toDate != null
            cityInputLayout.isEndIconVisible = eventFilterParam.city != null

            fromDateInputText.setText(eventFilterParam.fromDate?.let { DateUtils.formatDate(it, DateUtils.PATTERN) })
            toDateInputText.setText(eventFilterParam.toDate?.let { DateUtils.formatDate(it, DateUtils.PATTERN) })
            cityInputText.setText(eventFilterParam.city?.name)
            formatInputText.setText(eventFilterParam.format?.eventType?.let { getString(it) }, false)
        }
    }

    private val fromDatePickerResultListener = FragmentResultListener { _: String, bundle: Bundle ->

        val datePickerDialogResult = bundle.getSerializable(DatePickerFragment.RESULT_KEY) as DatePickerDialogResult?

        if (datePickerDialogResult != null) {
            with(datePickerDialogResult) {
                val localDate = LocalDate(
                    year,
                    month,
                    day
                ).toDate()
                viewModel.setFromDateFilter(localDate)
            }
        }
    }

    private val toDatePickerResultListener = FragmentResultListener { _: String, bundle: Bundle ->

        val datePickerDialogResult = bundle.getSerializable(DatePickerFragment.RESULT_KEY) as DatePickerDialogResult?

        if (datePickerDialogResult != null) {
            with(datePickerDialogResult) {
                val localDate = LocalDate(
                    year,
                    month,
                    day
                ).toDate()
                viewModel.setToDateFilter(localDate)
            }
        }
    }

    private val cityFragmentResultListener = FragmentResultListener { _: String, bundle: Bundle ->

        val cityPickerDialogResult = bundle.getSerializable(CityBottomSheet.RESULT_KEY) as CityItem?
        if (cityPickerDialogResult != null) {
            viewModel.setCityFilter(cityPickerDialogResult)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val RESULT_KEY = "isFilterActiveResult"

        const val REQUEST_KEY = 986

        fun createIntent(context: Context): Intent {
            return Intent(context, EventFilterActivity::class.java)
        }
    }
}