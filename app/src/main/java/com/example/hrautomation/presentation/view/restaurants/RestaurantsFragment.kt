package com.example.hrautomation.presentation.view.restaurants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hrautomation.R
import com.example.hrautomation.databinding.FragmentRestaurantsBinding
import com.example.hrautomation.utils.ui.Dp
import com.example.hrautomation.utils.ui.dpToPx
import com.google.android.material.tabs.TabLayoutMediator

class RestaurantsFragment : Fragment() {

    private var _binding: FragmentRestaurantsBinding? = null
    private val binding: FragmentRestaurantsBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRestaurantsBinding.inflate(inflater, container, false)

        initToolbar()

        initUi()

        return binding.root
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

    private fun initUi() {
        val pagerAdapter = RestaurantsPagerAdapter(this)
        with(binding) {
            viewPager.adapter = pagerAdapter
            viewPager.isUserInputEnabled = false
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    MAP_FRAGMENT_INT -> tab.text = getString(R.string.tab_map)
                    else -> tab.text = getString(R.string.tab_list)
                }
            }.attach()
        }
    }

    private companion object {
        @Dp
        const val TOOLBAR_ELEVATION = 4F

        const val MAP_FRAGMENT_INT = 0
    }
}