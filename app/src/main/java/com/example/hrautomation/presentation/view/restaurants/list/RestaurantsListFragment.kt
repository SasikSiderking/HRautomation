package com.example.hrautomation.presentation.view.restaurants.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.FragmentRestaurantsListBinding
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.view.restaurants.RestaurantsViewModel
import com.example.hrautomation.presentation.view.restaurants.restaurant_details.RestaurantDetailsActivity
import com.example.hrautomation.utils.ViewModelFactory
import com.example.hrautomation.utils.ui.Dp
import com.example.hrautomation.utils.ui.dpToPx
import javax.inject.Inject

class RestaurantsListFragment : Fragment() {

    private var _binding: FragmentRestaurantsListBinding? = null
    private val binding: FragmentRestaurantsListBinding
        get() = _binding!!

    private lateinit var adapter: RestaurantsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: RestaurantsViewModel by viewModels {
        viewModelFactory
    }

    private val restaurantsObserver = Observer<List<BaseListItem>> { restaurantList ->
        adapter.update(restaurantList)
    }

    private val onRestaurantClickListener = OnRestaurantClickListener { restaurantId: Long ->
        startActivity(RestaurantDetailsActivity.createIntent(requireContext(), restaurantId))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRestaurantsListBinding.inflate(inflater, container, false)

        initUi()

        initToolbar()

        return binding.root
    }

    private fun initUi() {
        adapter = RestaurantsAdapter(onRestaurantClickListener)
        binding.restaurantRecyclerView.adapter = adapter
        binding.restaurantRecyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        viewModel.restaurants.observe(viewLifecycleOwner, restaurantsObserver)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.let {
            it.elevation = requireContext().dpToPx(TOOLBAR_ELEVATION).toFloat()
        }
    }

    private companion object {
        @Dp
        const val TOOLBAR_ELEVATION = 4F
    }
}