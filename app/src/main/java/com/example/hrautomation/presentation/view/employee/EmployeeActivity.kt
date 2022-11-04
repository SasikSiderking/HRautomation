package com.example.hrautomation.presentation.view.employee

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityEmployeeBinding
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

    private fun initUi() {
        binding.employeeFullName.text = viewModel.getSelectedEmployee().name
        binding.employeeFullEmail.setText(viewModel.getSelectedEmployee().email)
        binding.employeeFullPost.setText(viewModel.getSelectedEmployee().post)
        binding.employeeFullProject.setText(viewModel.getSelectedEmployee().project)
        binding.employeeFullAbout.setText(viewModel.getSelectedEmployee().info)
    }
}