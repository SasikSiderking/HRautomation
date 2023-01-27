package com.example.hrautomation.presentation.view.restaurants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hrautomation.databinding.FragmentRestaurantsListBinding
import com.example.hrautomation.utils.ui.Dp
import com.example.hrautomation.utils.ui.dpToPx

class RestaurantsListFragment : Fragment() {

    private var _binding: FragmentRestaurantsListBinding? = null
    private val binding: FragmentRestaurantsListBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRestaurantsListBinding.inflate(inflater, container, false)

        initToolbar()

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

    private companion object {
        @Dp
        const val TOOLBAR_ELEVATION = 4F
    }
}