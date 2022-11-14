package com.example.hrautomation.presentation.view.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.FragmentProductBinding
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.ViewModelFactory
import javax.inject.Inject

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding: FragmentProductBinding
        get() = _binding!!

    private lateinit var adapter: ProductAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ProductFragmentViewModel by viewModels {
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
        _binding = FragmentProductBinding.inflate(inflater, container, false)

        initUi()

        viewModel.data.observe(viewLifecycleOwner, productObserver)

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initUi() {
        adapter = ProductAdapter()
        binding.productRecyclerview.adapter = adapter
    }

    private val productObserver = Observer<List<BaseListItem>> { newItems ->
        adapter.update(newItems)
    }
}