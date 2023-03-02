package com.example.hrautomation.presentation.view.restaurants.restaurant_bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.BottomSheetRestaurantsBinding
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.view.restaurants.list.OnRestaurantClickListener
import com.example.hrautomation.utils.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class RestaurantBottomSheet : BottomSheetDialogFragment() {

    private val buildingId: Long by lazy { requireArguments().getLong(BUILDING_ID_BUNDLE_KEY) }

    private var _binding: BottomSheetRestaurantsBinding? = null
    private val binding: BottomSheetRestaurantsBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: RestaurantBottomSheetViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var adapter: BottomSheetRestaurantsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomSheetRestaurantsBinding.inflate(inflater, container, false)

        initUi()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialog = dialog
        if (dialog != null) {
            val bottomSheet = dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.addBottomSheetCallback(callback)
        }
    }

    private val callback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                viewModel.loadAllData()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit

    }

    private fun initUi() {

        if (buildingId != null) {
            viewModel.loadData(buildingId)
        }

        adapter = BottomSheetRestaurantsAdapter(onRestaurantClickListener)
        binding.restaurantRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner, restaurantsObserver)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private val restaurantsObserver = Observer<List<BaseListItem>> { restaurantList ->
        adapter.update(restaurantList)
    }

    private val onRestaurantClickListener = OnRestaurantClickListener {
//        TODO(Открыть активити с фулл рестораном)
    }

    companion object {
        const val TAG = "RestaurantBottomSheet"

        private const val BUILDING_ID_BUNDLE_KEY = "IdKey"

        fun newInstance(buildingId: Long): RestaurantBottomSheet {
            return RestaurantBottomSheet().apply {
                arguments = bundleOf(
                    BUILDING_ID_BUNDLE_KEY to buildingId
                )
            }
        }
    }

}