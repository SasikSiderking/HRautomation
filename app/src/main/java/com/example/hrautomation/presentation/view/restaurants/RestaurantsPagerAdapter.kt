package com.example.hrautomation.presentation.view.restaurants

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hrautomation.presentation.view.restaurants.list.RestaurantsListFragment
import com.example.hrautomation.presentation.view.restaurants.map.RestaurantsMapFragment

class RestaurantsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val restaurantsMapFragment = RestaurantsMapFragment()
    private val restaurantsListFragment = RestaurantsListFragment()

    override fun getItemCount(): Int {
        return TAB_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            MAP_FRAGMENT_INT -> restaurantsMapFragment
            LIST_FRAGMENT_INT -> restaurantsListFragment
            else -> throw IllegalStateException("Incorrect tab position")
        }
    }


    private companion object {
        const val TAB_COUNT = 2
        const val MAP_FRAGMENT_INT = 0
        const val LIST_FRAGMENT_INT = 1
    }
}