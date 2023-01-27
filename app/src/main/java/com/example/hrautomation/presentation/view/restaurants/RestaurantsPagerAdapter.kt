package com.example.hrautomation.presentation.view.restaurants

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class RestaurantsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
//        ыыы
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
//            ыыы
            0 -> MapFragment()
            else -> RestaurantsListFragment()
        }
//        TODO("Убрать 'ыыы'")
    }

}