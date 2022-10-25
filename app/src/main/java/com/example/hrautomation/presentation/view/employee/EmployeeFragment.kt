package com.example.hrautomation.presentation.view.employee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.hrautomation.R
import com.example.hrautomation.databinding.FragmentEmployeeBinding
import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.presentation.view.activity.appComponent
import com.example.hrautomation.presentation.view.colleagues.ColleaguesFragmentViewModel
import com.example.hrautomation.utils.ViewModelFactory
import javax.inject.Inject

class EmployeeFragment : Fragment() {
    private var _binding: FragmentEmployeeBinding? = null
    private val binding: FragmentEmployeeBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel: ColleaguesFragmentViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedEmployee.observe(viewLifecycleOwner, selectedEmployeeObserver)
    }

    override fun onDestroyView() {
        _binding?.unbind()
        _binding = null
        super.onDestroyView()
    }

    private val selectedEmployeeObserver = Observer<Employee> {
        binding.employeeFullName.text = it.name
        binding.employeeFullEmail.setText(it.email)
        binding.employeeFullPost.setText(it.post)
        binding.employeeFullProject.setText(it.project)
        binding.employeeFullAbout.setText(it.info)

    }
}