package com.example.hrautomation.presentation.view.employee

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityEmployeeBinding
import com.example.hrautomation.presentation.model.ColleagueItem
import com.example.hrautomation.utils.ViewModelFactory
import javax.inject.Inject

class EmployeeActivity : AppCompatActivity() {
    private var _binding: ActivityEmployeeBinding? = null
    private val binding: ActivityEmployeeBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: EmployeeViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as App).appComponent.inject(this)

        _binding = ActivityEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
    }

    override fun onDestroy() {
        _binding?.unbind()
        _binding = null
        super.onDestroy()
    }

    private val selectedEmployeeObserver = Observer<ColleagueItem> { colleague ->
        binding.employeeFullName.text = colleague.name
        binding.employeeFullEmail.setText(colleague.email)
        binding.employeeFullPost.setText(colleague.post)
        binding.employeeFullProject.setText(colleague.project)
        binding.employeeFullAbout.setText(colleague.info)
    }

    private fun initUi() {
        viewModel.selectedEmployee.observe(this, selectedEmployeeObserver)
    }
}