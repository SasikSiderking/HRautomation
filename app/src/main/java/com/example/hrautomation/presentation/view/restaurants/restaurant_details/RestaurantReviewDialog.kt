package com.example.hrautomation.presentation.view.restaurants.restaurant_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.hrautomation.R
import com.example.hrautomation.databinding.FragmentRestaurantReviewDialogBinding
import com.example.hrautomation.presentation.model.restaurants.ReviewDialogResult

class RestaurantReviewDialog : DialogFragment() {

    private var _binding: FragmentRestaurantReviewDialogBinding? = null
    private val binding: FragmentRestaurantReviewDialogBinding
        get() = _binding!!

    private val restaurantName: String by lazy { requireArguments().getString(RESTAURANT_NAME)!! }

    private val resultBundle = Bundle()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.setTitle(getString(R.string.review_title, restaurantName))
        _binding = FragmentRestaurantReviewDialogBinding.inflate(inflater, container, false)
        binding.addButton.setOnClickListener(onAddButtonClickListener)
        binding.cancelButton.setOnClickListener(onCancelButtonClickListener)
        return binding.root
    }

    private val onAddButtonClickListener = OnClickListener { _ ->
        with(binding) {
            if (checkField.text.toString().isNotEmpty()) {
                val reviewDialogResult = ReviewDialogResult(
                    contentField.text.toString(),
                    checkField.text.toString().toFloat().toInt(),
                    reviewRating.rating
                )
                resultBundle.putSerializable(RESULT, reviewDialogResult)
                setFragmentResult(TAG, resultBundle)
                dismiss()
            } else {
                Toast.makeText(context, getString(R.string.please_enter_your_check_sum), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val onCancelButtonClickListener = OnClickListener { _ ->
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "reviewDialog"

        const val RESTAURANT_NAME = "restaurantName"

        const val RESULT = "reviewDialogResult"

        fun newInstance(restaurantName: String): RestaurantReviewDialog {
            return RestaurantReviewDialog().apply {
                arguments = bundleOf(
                    RESTAURANT_NAME to restaurantName
                )
            }
        }
    }
}