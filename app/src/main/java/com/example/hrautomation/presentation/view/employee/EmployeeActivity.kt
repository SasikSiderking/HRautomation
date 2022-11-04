package com.example.hrautomation.presentation.view.employee

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityEmployeeBinding
import com.example.hrautomation.databinding.FragmentEmployeeBinding
import com.example.hrautomation.domain.model.Employee
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

    private val selectedEmployeeObserver = Observer<Employee> { employee ->
        binding.employeeFullName.text = employee.name
        binding.employeeFullEmail.setText(employee.email)
        binding.employeeFullPost.setText(employee.post)
        binding.employeeFullProject.setText(employee.project)
        binding.employeeFullAbout.setText(employee.info)
    }

    private fun initUi() {
        viewModel.selectedEmployee.observe(viewLifecycleOwner, selectedEmployeeObserver)
    }
}