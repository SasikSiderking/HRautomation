package com.example.hrautomation.presentation.view.product

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hrautomation.R
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.FragmentProductBinding
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.model.products.ProductCategoryItem
import com.example.hrautomation.utils.ViewModelFactory
import com.example.hrautomation.utils.ui.switcher.ContentLoadingSettings
import com.example.hrautomation.utils.ui.switcher.ContentLoadingState
import com.example.hrautomation.utils.ui.switcher.ContentLoadingStateSwitcher
import com.example.hrautomation.utils.ui.switcher.base.SwitchAnimationParams
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

    private val contentLoadingSwitcher: ContentLoadingStateSwitcher = ContentLoadingStateSwitcher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)

        initToolbar()
        initUi()

        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds -> chooseCategory(group, checkedIds) }

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.let {
            it.elevation = 0F
        }
    }

    private fun initUi() {
        with(binding) {
            contentLoadingSwitcher.setup(
                ContentLoadingSettings(
                    contentViews = listOf(coordinatorLayout),
                    errorViews = listOf(reusableReload.reusableReload),
                    loadingViews = listOf(reusableLoading.progressBar),
                    initState = ContentLoadingState.LOADING
                )
            )

            adapter = ProductAdapter(OnProductClickListener { id: Long, name: String ->
                showOrderDialog(id, name)
            })
            binding.productRecyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            binding.productRecyclerview.adapter = adapter

            reusableReload.reloadButton.setOnClickListener {
                viewModel.reload()
                contentLoadingSwitcher.switchState(ContentLoadingState.LOADING, SwitchAnimationParams(delay = 500L))
            }
            productRecyclerview.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }

        viewModel.data.observe(viewLifecycleOwner, productObserver)
        viewModel.categories.observe(viewLifecycleOwner, categoriesObserver)
        viewModel.exception.observe(viewLifecycleOwner, exceptionObserver)
        viewModel.message.observe(viewLifecycleOwner, messageObserver)
    }

    private val productObserver = Observer<List<BaseListItem>> { newItems ->
        adapter.update(newItems)
        contentLoadingSwitcher.switchState(ContentLoadingState.CONTENT, SwitchAnimationParams(delay = 500L))
    }
    private val categoriesObserver = Observer<List<ProductCategoryItem>> { newItems ->
        fillChipGroup(newItems)
    }

    private fun fillChipGroup(list: List<ProductCategoryItem>) {
        binding.chipGroup.removeAllViews()
        list.forEach { category ->
            val chip = Chip(context).apply {
                id = category.id.toInt()
                text = category.name
                setChipBackgroundColorResource(R.color.white)
                isClickable = true
                isCheckable = true
            }
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
            contentLoadingSwitcher.switchState(ContentLoadingState.ERROR, SwitchAnimationParams(delay = 500L))
        }
    }

    private val messageObserver = Observer<Int?> { stringId ->
        stringId?.let {
            Toast.makeText(requireContext(), getString(stringId), Toast.LENGTH_SHORT).show()
            viewModel.clearMessageState()
        }
    }

    private fun showOrderDialog(id: Long, name: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.apply {
            setPositiveButton(
                getString(R.string.order_product_dialog_positive),
                DialogInterface.OnClickListener { _, _ ->
                    viewModel.orderProduct(id)
                }
            )
            setNegativeButton(getString(R.string.order_product_dialog_negative),
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.cancel()
                }
            )
        }
        builder
            .setMessage(getString(R.string.order_product_dialog_message, name))
            .setTitle(getString(R.string.order_product_dialog_title))
        builder.show()
    }
}