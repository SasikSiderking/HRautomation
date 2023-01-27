package com.example.hrautomation.presentation.view.restaurants

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class RestaurantsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return TAB_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            MAP_FRAGMENT_INT -> MapFragment()
            else -> RestaurantsListFragment()
        }
    }


    private companion object {
        const val TAB_COUNT = 2
        const val MAP_FRAGMENT_INT = 0
    }
}