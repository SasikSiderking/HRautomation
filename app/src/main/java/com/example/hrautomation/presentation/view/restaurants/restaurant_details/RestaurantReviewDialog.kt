package com.example.hrautomation.presentation.view.restaurants.restaurant_details

import android.graphics.Insets
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.hrautomation.R
import com.example.hrautomation.databinding.FragmentRestaurantReviewDialogBinding

class RestaurantReviewDialog : DialogFragment(), OnClickListener {

    private var _binding: FragmentRestaurantReviewDialogBinding? = null
    private val binding: FragmentRestaurantReviewDialogBinding
        get() = _binding!!

    private val restaurantName: String by lazy { requireArguments().getString(RESTAURANT_NAME)!! }

    private val resultBundle = Bundle()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.setTitle(getString(R.string.review_title, restaurantName))
        _binding = FragmentRestaurantReviewDialogBinding.inflate(inflater, container, false)
        binding.addButton.setOnClickListener(this)
        binding.cancelButton.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(view: View?) {
        with(binding) {
            when (view) {
                addButton -> {
                    if (checkField.text.toString().isNotEmpty()) {
                        resultBundle.putString(REVIEW_CONTENT, contentField.text.toString())
                        resultBundle.putInt(REVIEW_CHECK, checkField.text.toString().toFloat().toInt())
                        resultBundle.putFloat(REVIEW_RATING, reviewRating.rating)
                        setFragmentResult(TAG, resultBundle)
                        dismiss()
                    } else {
                        Toast.makeText(context, getString(R.string.please_enter_your_check_sum), Toast.LENGTH_SHORT).show()
                    }
                }

                cancelButton -> {
                    dismiss()
                }
                else -> Unit
            }
        }
    }

    //    Я украл этот код тобы окошко было больше чем дефолтное. Кажется, это не делается простым способом
    override fun onResume() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics =
                requireActivity().windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            val width = windowMetrics.bounds.width() - insets.left -
                    insets.right
            val height = windowMetrics.bounds.height() - insets.top -
                    insets.bottom
            val window = dialog!!.window
            if (window != null) {
                window.setLayout(
                    (width * 0.90).toInt(), (height *
                            0.90).toInt()
                ) // for width and height to be 90 % of screen
                window.setGravity(Gravity.CENTER)
            }
            super.onResume()
        } else {
            val window = dialog!!.window
            val size = Point()
            // Store dimensions of the screen in `size`
            val display = window!!.windowManager.defaultDisplay
            display.getSize(size)
            // Set the width of the dialog proportional to 90% of the screen width
            window.setLayout(
                (size.x * 0.90).toInt(),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            window.setGravity(Gravity.CENTER)
            // Call super onResume after sizing
            super.onResume()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "reviewDialog"

        const val REVIEW_CONTENT = "reviewContent"

        const val REVIEW_CHECK = "reviewCheck"

        const val REVIEW_RATING = "reviewRating"

        const val RESTAURANT_NAME = "restaurantName"

        fun newInstance(restaurantName: String): RestaurantReviewDialog {
            return RestaurantReviewDialog().apply {
                arguments = bundleOf(
                    RESTAURANT_NAME to restaurantName
                )
            }
        }
    }
}