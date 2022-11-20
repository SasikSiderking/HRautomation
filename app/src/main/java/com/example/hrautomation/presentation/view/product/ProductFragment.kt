package com.example.hrautomation.presentation.view.product

import android.app.AlertDialog
import android.content.DialogInterface
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
import com.example.hrautomation.presentation.model.ProductCategoryItem
import com.example.hrautomation.utils.ViewModelFactory
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import javax.inject.Inject

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding: FragmentProductBinding
        get() = _binding!!

    private lateinit var adapter: ProductAdapter


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

        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds -> chooseCategory(group, checkedIds) }

        return binding.root
    }

    override fun onDestroyView() {
        _binding?.unbind()
        _binding = null
        viewModel.clearToastState()
        super.onDestroyView()
    }

    private fun initUi() {
        adapter = ProductAdapter(OnProductClickListener { id: Long ->
            showOrderDialog(id)
        })
        binding.productRecyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.productRecyclerview.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner, productObserver)
        viewModel.categories.observe(viewLifecycleOwner, categoriesObserver)
        viewModel.exception.observe(viewLifecycleOwner, exceptionObserver)
        viewModel.message.observe(viewLifecycleOwner, messageObserver)
    }

    private val productObserver = Observer<List<BaseListItem>> { newItems ->
        adapter.update(newItems)
    }
    private val categoriesObserver = Observer<List<ProductCategoryItem>> { newItems ->
        fillChipGroup(newItems)
    }

    private fun fillChipGroup(list: List<ProductCategoryItem>) {
        list.forEach { category ->
            val chip = Chip(context)
            chip.id = category.id.toInt()
            chip.text = category.name
            chip.textSize = 22F
            chip.isClickable = true
            chip.isCheckable = true
            binding.chipGroup.addView(chip)
        }
    }

    private fun chooseCategory(group: ChipGroup, checkedIds: List<Int>) {
        var categoryId: Long? = null
        checkedIds.firstOrNull()?.let { checkedId ->
            val chip: Chip = group.findViewById(checkedId)
            categoryId = chip.id.toLong()
        }
        viewModel.loadProductsByCategory(categoryId)
    }

    private val exceptionObserver = Observer<Throwable?> { exception ->
        exception?.let {
            Toast.makeText(requireContext(), "Что-то пошло не так", Toast.LENGTH_LONG).show()
        }
    }

    private val messageObserver = Observer<String?> { message ->
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showOrderDialog(id: Long) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.apply {
            setPositiveButton(
                "Да",
                DialogInterface.OnClickListener { _, _ ->
                    viewModel.orderProduct(id)
                }
            )
            setNegativeButton("Нет",
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.cancel()
                }
            )
        }
        builder
            .setMessage("Заказать?")
            .setTitle("Вы уверены?")
        builder.create()
        builder.show()
    }
}