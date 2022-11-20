package com.example.hrautomation.presentation.view.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private lateinit var categoryAdapter: ProductCategoryAdapter


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ProductViewModel by viewModels {
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
        adapter = ProductAdapter()
        binding.productRecyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.productRecyclerview.adapter = adapter

        categoryAdapter = ProductCategoryAdapter()
        binding.categoryRecyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.categoryRecyclerview.adapter = categoryAdapter

        viewModel.data.observe(viewLifecycleOwner, productObserver)
        viewModel.categories.observe(viewLifecycleOwner, categoriesObserver)
        viewModel.exception.observe(viewLifecycleOwner, exceptionObserver)
    }

    private val productObserver = Observer<List<BaseListItem>> { newItems ->
        adapter.update(newItems)
    }
    private val categoriesObserver = Observer<List<BaseListItem>> { newItems ->
        categoryAdapter.update(newItems)
    }

    private val exceptionObserver = Observer<Throwable?> { exception ->
        exception?.let {
            Toast.makeText(requireContext(), "Что-то пошло не так", Toast.LENGTH_LONG).show()

            viewModel.setToastShownState()
        }
    }
}