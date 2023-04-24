package com.example.hrautomation.presentation.view.social.filter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.example.hrautomation.R
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityEventFilterBinding
import com.example.hrautomation.presentation.base.activity.BaseActivity
import com.example.hrautomation.presentation.model.restaurants.CityItem
import com.example.hrautomation.presentation.model.social.DatePickerDialogResult
import com.example.hrautomation.presentation.model.social.EventFormat
import com.example.hrautomation.presentation.view.restaurants.сity.CityBottomSheet
import com.example.hrautomation.utils.date.DateUtils
import com.example.hrautomation.utils.ui.switcher.ContentLoadingSettings
import com.example.hrautomation.utils.ui.switcher.ContentLoadingState
import org.joda.time.LocalDate
import timber.log.Timber

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
            fromDateInputText.showSoftInputOnFocus = false
            toDateInputText.showSoftInputOnFocus = false
            cityInputText.showSoftInputOnFocus = false
            formatInputText.showSoftInputOnFocus = false

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
            formatInputLayout.setEndIconOnClickListener {
                viewModel.setFormatFilter(null)
                formatInputText.text?.clear()
            }


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

    override fun initObserves() = Unit

    override fun initListeners() {

        val arrayAdapter = ArrayAdapter(this, R.layout.event_format_dropdown_item, EventFormat.values())
        Timber.e(EventFormat.values().toString())
        binding.formatInputText.setAdapter(arrayAdapter)

        binding.fromDateInputText.setOnTouchListener(InputTextTouchListener({
            val newFragment = DatePickerFragment.newInstance(DatePickerFragment.FROM_REQUEST)
            newFragment.show(supportFragmentManager, DatePickerFragment.TAG)
        }))
        binding.toDateInputText.setOnTouchListener(InputTextTouchListener({
            val newFragment = DatePickerFragment.newInstance(DatePickerFragment.TO_REQUEST)
            newFragment.show(supportFragmentManager, DatePickerFragment.TAG)
        }))
        binding.cityInputText.setOnTouchListener(InputTextTouchListener({
            val newFragment = CityBottomSheet.newInstance()
            newFragment.show(supportFragmentManager, CityBottomSheet.TAG)
        }))
        binding.formatInputText.setOnItemClickListener { adapterView, view, position, id ->
            val selectedFormat = arrayAdapter.getItem(position)
            viewModel.setFormatFilter(selectedFormat)
        }

        supportFragmentManager.setFragmentResultListener(
            DatePickerFragment.FROM_REQUEST,
            this
        ) { _: String, bundle: Bundle ->

            val dateInputText = binding.fromDateInputText

            val datePickerDialogResult = bundle.getSerializable(DatePickerFragment.RESULT_KEY) as DatePickerDialogResult?

            if (datePickerDialogResult != null) {
                with(datePickerDialogResult) {
                    val localDate = LocalDate(
                        year,
                        month,
                        day
                    ).toDate()
                    dateInputText.setText(DateUtils.formatDate(localDate))
                    viewModel.setFromDateFilter(localDate)
                }
            }
        }

        supportFragmentManager.setFragmentResultListener(
            DatePickerFragment.TO_REQUEST,
            this
        ) { _: String, bundle: Bundle ->
            val dateInputText = binding.toDateInputText

            val datePickerDialogResult = bundle.getSerializable(DatePickerFragment.RESULT_KEY) as DatePickerDialogResult?

            if (datePickerDialogResult != null) {
                with(datePickerDialogResult) {
                    val localDate = LocalDate(
                        year,
                        month,
                        day
                    ).toDate()
                    dateInputText.setText(DateUtils.formatDate(localDate))
                    viewModel.setFromDateFilter(localDate)
                }
            }
        }

        supportFragmentManager.setFragmentResultListener(
            CityBottomSheet.REQUEST_KEY,
            this
        ) { _: String, bundle: Bundle ->

            val cityPickerDialogResult = bundle.getSerializable(CityBottomSheet.RESULT_KEY) as CityItem?
            if (cityPickerDialogResult != null) {
                binding.cityInputText.setText(cityPickerDialogResult.name)
                viewModel.setCityFilter(cityPickerDialogResult.id)
            }
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

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, EventFilterActivity::class.java)
        }
    }
}