package com.example.hrautomation.presentation.view.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hrautomation.R
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.FragmentProductBinding
import com.example.hrautomation.utils.ViewModelFactory
import javax.inject.Inject

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding: FragmentProductBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel: ProductFragmentViewModel by viewModels {
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
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onDestroyView() {
        _binding?.unbind()
        _binding = null
        super.onDestroyView()
    }
}