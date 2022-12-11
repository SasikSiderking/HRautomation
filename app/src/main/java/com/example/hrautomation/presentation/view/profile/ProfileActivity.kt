package com.example.hrautomation.presentation.view.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.hrautomation.R
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityProfileBinding
import com.example.hrautomation.presentation.model.colleagues.EmployeeItem
import com.example.hrautomation.utils.ViewModelFactory
import com.example.hrautomation.utils.ui.switcher.ContentLoadingSettings
import com.example.hrautomation.utils.ui.switcher.ContentLoadingState
import com.example.hrautomation.utils.ui.switcher.ContentLoadingStateSwitcher
import com.example.hrautomation.utils.ui.switcher.base.SwitchAnimationParams
import javax.inject.Inject

class ProfileActivity : AppCompatActivity() {

    private var _binding: ActivityProfileBinding? = null
    private val binding: ActivityProfileBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ProfileViewModel by viewModels {
        viewModelFactory
    }

    private val contentLoadingSwitcher: ContentLoadingStateSwitcher = ContentLoadingStateSwitcher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as App).appComponent.inject(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        _binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private val employeeObserver = Observer<EmployeeItem> { employeeItem ->
        with(binding) {
            Glide.with(employeeImageView)
                .load("https://cdn.mos.cms.futurecdn.net/PzPq6Pbn5RqgrWunhEx6rg.jpg")
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(employeeImageView)
            employeeFullName.setText(employeeItem.name)
            employeeFullEmail.setText(employeeItem.email)
            employeeFullPost.setText(employeeItem.post)
            employeeFullProject.setText(employeeItem.project)
            employeeFullAbout.setText(employeeItem.info)
        }

        contentLoadingSwitcher.switchState(ContentLoadingState.CONTENT, SwitchAnimationParams(delay = 500L))
    }

    private val exceptionObserver = Observer<Throwable?> { exception ->
        exception?.let {
            contentLoadingSwitcher.switchState(ContentLoadingState.ERROR, SwitchAnimationParams(delay = 500L))
        }
    }

    private val messageObserver = Observer<Int?> { stringId ->
        stringId?.let {
            Toast.makeText(this, getString(stringId), Toast.LENGTH_SHORT).show()
            viewModel.clearMessageState()
        }
    }

    private fun initUi() {
        with(binding) {
            contentLoadingSwitcher.setup(
                ContentLoadingSettings(
                    contentViews = listOf(scrollView3),
                    errorViews = listOf(reusableReload.reusableReload),
                    loadingViews = listOf(reusableLoading.progressBar),
                    initState = ContentLoadingState.LOADING
                )
            )

            saveButton.setOnClickListener { _ ->
                viewModel.saveData(
                    employeeFullProject.text.toString(),
                    employeeFullAbout.text.toString()
                )
            }

            reusableReload.reloadButton.setOnClickListener {
                viewModel.reload()
                contentLoadingSwitcher.switchState(ContentLoadingState.LOADING, SwitchAnimationParams(delay = 500L))
            }
        }
        viewModel.data.observe(this, employeeObserver)
        viewModel.exception.observe(this, exceptionObserver)
        viewModel.message.observe(this, messageObserver)
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ProfileActivity::class.java)
        }
    }
}