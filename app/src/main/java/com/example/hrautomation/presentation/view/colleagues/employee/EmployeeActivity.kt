package com.example.hrautomation.presentation.view.colleagues.employee

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.hrautomation.R
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityEmployeeBinding
import com.example.hrautomation.presentation.model.colleagues.EmployeeItem
import com.example.hrautomation.utils.ViewModelFactory
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
        binding.employeeFullName.setText(colleague.name)
        binding.employeeFullEmail.setText(colleague.email)
        binding.employeeFullPost.setText(colleague.post)
        binding.employeeFullProject.setText(colleague.project)
        binding.employeeFullAbout.setText(colleague.info)
        supportActionBar?.title = colleague.name
    }

    private val exceptionObserver = Observer<Throwable?> { exception ->
        exception?.let {
            Toast.makeText(this, R.string.toast_overall_error, Toast.LENGTH_LONG).show()
            viewModel.clearExceptionState()
        }
    }
    private val isLoadingObserver = Observer<Boolean> { isLoading ->
        binding.progressBar.isVisible = isLoading
    }

    private fun initUi() {
        viewModel.selectedEmployee.observe(this, selectedEmployeeObserver)
        viewModel.exception.observe(this, exceptionObserver)
        viewModel.isLoading.observe(this, isLoadingObserver)
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