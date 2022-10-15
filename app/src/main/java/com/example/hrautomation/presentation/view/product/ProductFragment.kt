package com.example.hrautomation.presentation.view.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hrautomation.R
import com.example.hrautomation.databinding.FragmentProductBinding
import com.example.hrautomation.presentation.view.activity.appComponent
import javax.inject.Inject

class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding

    @Inject
    lateinit var productFragmentViewModelProvider: ProductFragmentViewModelProvider

    //        Подключаем ViewModel
    private val viewModel: ProductFragmentViewModel by viewModels {
        productFragmentViewModelProvider
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        Подключаем binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//         Редактруем рекуклер вью
//        binding.productRecyclerview.layoutManager = GridLayoutManager(context, 3)
    }
}