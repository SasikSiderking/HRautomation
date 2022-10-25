package com.example.hrautomation.presentation.view.colleagues

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.hrautomation.R
import com.example.hrautomation.databinding.FragmentColleaguesBinding
import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.presentation.view.activity.appComponent
import com.example.hrautomation.utils.ViewModelFactory
import javax.inject.Inject

class ColleaguesFragment : Fragment() {

    private var _binding: FragmentColleaguesBinding? = null
    private val binding: FragmentColleaguesBinding
        get() = _binding!!

    private val adapter get() = binding.colleaguesRecyclerview.adapter as? ColleaguesFragmentRecyclerViewAdapter

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
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_colleagues, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.colleaguesRecyclerview.adapter = viewModel.data.value?.let {
            ColleaguesFragmentRecyclerViewAdapter(
                it, OnEmployeeClickListener { employee ->
                    findNavController().navigate(R.id.action_colleaguesFragment_to_employeeFragment)
                    viewModel.selectEmployee(employee)
                }
            )
        }
        viewModel.data.observe(viewLifecycleOwner, colleaguesObserver)
    }

    private val colleaguesObserver = Observer<List<Employee>> {
        adapter?.update(it)
    }

    override fun onDestroyView() {
        _binding?.unbind()
        _binding = null
        super.onDestroyView()
    }
}