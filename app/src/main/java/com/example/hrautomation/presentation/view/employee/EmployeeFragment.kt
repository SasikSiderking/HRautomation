package com.example.hrautomation.presentation.view.employee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.FragmentEmployeeBinding
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

    private fun initUi() {
        binding.employeeFullName.text = viewModel.getSelectedEmployee().name
        binding.employeeFullEmail.setText(viewModel.getSelectedEmployee().email)
        binding.employeeFullPost.setText(viewModel.getSelectedEmployee().post)
        binding.employeeFullProject.setText(viewModel.getSelectedEmployee().project)
        binding.employeeFullAbout.setText(viewModel.getSelectedEmployee().info)
    }
}