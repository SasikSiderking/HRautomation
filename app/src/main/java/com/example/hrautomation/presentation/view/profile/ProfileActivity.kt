package com.example.hrautomation.presentation.view.profile

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
import com.example.hrautomation.databinding.ActivityProfileBinding
import com.example.hrautomation.presentation.model.EmployeeItem
import com.example.hrautomation.utils.ViewModelFactory
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
        binding.employeeFullName.setText(employeeItem.name)
        binding.employeeFullEmail.setText(employeeItem.email)
        binding.employeeFullPost.setText(employeeItem.post)
        binding.employeeFullProject.setText(employeeItem.project)
        binding.employeeFullAbout.setText(employeeItem.info)
    }

    private val exceptionObserver = Observer<Throwable?> { exception ->
        exception?.let {
            Toast.makeText(this, R.string.toast_overall_error, Toast.LENGTH_SHORT).show()
            viewModel.clearExceptionState()
        }
    }
    private val isLoadingObserver = Observer<Boolean> { isLoading ->
        binding.progressBar.isVisible = isLoading
    }

    private val messageObserver = Observer<Int?> { stringId ->
        stringId?.let {
            Toast.makeText(this, getString(stringId), Toast.LENGTH_SHORT).show()
            viewModel.clearMessageState()
        }
    }

    private fun initUi() {
        with(binding) {
            saveButton.setOnClickListener { _ ->
                viewModel.saveData(
                    employeeFullProject.text.toString(),
                    employeeFullAbout.text.toString()
                )
            }
        }
        viewModel.data.observe(this, employeeObserver)
        viewModel.exception.observe(this, exceptionObserver)
        viewModel.message.observe(this, messageObserver)
        viewModel.isLoading.observe(this, isLoadingObserver)
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ProfileActivity::class.java)
        }
    }
}