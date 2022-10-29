package com.example.hrautomation.presentation.view.employee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.FragmentEmployeeBinding
import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.utils.ViewModelFactory
import javax.inject.Inject

class EmployeeFragment : Fragment() {
    private var _binding: FragmentEmployeeBinding? = null
    private val binding: FragmentEmployeeBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: EmployeeViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmployeeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        initUi()

        return binding.root
    }

    override fun onDestroyView() {
        _binding?.unbind()
        _binding = null
        super.onDestroyView()
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