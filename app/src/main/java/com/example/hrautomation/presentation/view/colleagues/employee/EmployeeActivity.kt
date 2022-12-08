package com.example.hrautomation.presentation.view.colleagues.employee

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
import com.example.hrautomation.databinding.ActivityEmployeeBinding
import com.example.hrautomation.presentation.model.colleagues.EmployeeItem
import com.example.hrautomation.utils.ViewModelFactory
import com.example.hrautomation.utils.ui.switcher.ContentLoadingSettings
import com.example.hrautomation.utils.ui.switcher.ContentLoadingState
import com.example.hrautomation.utils.ui.switcher.ContentLoadingStateSwitcher
import com.example.hrautomation.utils.ui.switcher.base.SwitchAnimationParams
import javax.inject.Inject

class EmployeeActivity : AppCompatActivity() {
    private var _binding: ActivityEmployeeBinding? = null
    private val binding: ActivityEmployeeBinding
        get() = _binding!!

    private val selectedEmployeeId: Long by lazy { intent.getLongExtra(ID_EXTRA, 0L) }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: EmployeeViewModel by viewModels {
        viewModelFactory
    }

    private val contentLoadingSwitcher: ContentLoadingStateSwitcher = ContentLoadingStateSwitcher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as App).appComponent.inject(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        _binding = ActivityEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()

        viewModel.loadData(selectedEmployeeId)
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

    private val selectedEmployeeObserver = Observer<EmployeeItem> { colleague ->
        with(binding) {
            Glide.with(employeeImageView)
                .load(colleague.img)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(employeeImageView)
            employeeFullName.setText(colleague.name)
            employeeFullEmail.setText(colleague.email)
            employeeFullPost.setText(colleague.post)
            employeeFullProject.setText(colleague.project)
            employeeFullAbout.setText(colleague.info)
        }
        supportActionBar?.title = colleague.name

        contentLoadingSwitcher.switchState(ContentLoadingState.CONTENT, SwitchAnimationParams(delay = 500L))
    }

    private val exceptionObserver = Observer<Throwable?> { exception ->
        exception?.let {
            Toast.makeText(this, R.string.toast_overall_error, Toast.LENGTH_LONG).show()
            viewModel.clearExceptionState()
            contentLoadingSwitcher.switchState(ContentLoadingState.ERROR, SwitchAnimationParams(delay = 500L))
        }
    }

    private fun initUi() {
        with(binding) {
            contentLoadingSwitcher.setup(
                ContentLoadingSettings(
                    contentViews = listOf(scrollView2),
                    errorViews = listOf(reusableReload.reusableReload),
                    loadingViews = listOf(reusableLoading.progressBar),
                    initState = ContentLoadingState.LOADING
                )
            )
            reusableReload.reloadButton.setOnClickListener {
                viewModel.reload(selectedEmployeeId)
                contentLoadingSwitcher.switchState(ContentLoadingState.LOADING, SwitchAnimationParams(delay = 500L))
            }
        }
        viewModel.selectedEmployee.observe(this, selectedEmployeeObserver)
        viewModel.exception.observe(this, exceptionObserver)
    }

    companion object {
        private const val ID_EXTRA = "selectedEmployeeId"

        fun createIntent(context: Context, id: Long): Intent {
            val intent = Intent(context, EmployeeActivity::class.java)
            intent.putExtra(ID_EXTRA, id)
            return intent
        }
    }
}