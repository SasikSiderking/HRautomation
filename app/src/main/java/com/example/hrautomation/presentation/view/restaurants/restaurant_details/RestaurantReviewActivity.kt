package com.example.hrautomation.presentation.view.restaurants.restaurant_details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View.OnClickListener
import android.widget.RatingBar.OnRatingBarChangeListener
import com.example.hrautomation.R
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityRestaurantReviewBinding
import com.example.hrautomation.presentation.base.activity.BaseActivity
import com.example.hrautomation.presentation.model.restaurants.ReviewActivityResult
import com.example.hrautomation.utils.ui.switcher.ContentLoadingSettings
import com.example.hrautomation.utils.ui.switcher.ContentLoadingState
import com.example.hrautomation.utils.ui.switcher.base.SwitchAnimationParams
import com.google.android.material.snackbar.Snackbar

class RestaurantReviewActivity : BaseActivity<ActivityRestaurantReviewBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityRestaurantReviewBinding
        get() = { ActivityRestaurantReviewBinding.inflate(layoutInflater) }

    private val restaurantName: String by lazy { intent.getStringExtra(RESTAURANT_NAME)!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = restaurantName
    }

    override fun initInject() {
        (applicationContext as App).appComponent.inject(this)
    }

    override fun initUI() {
        with(binding) {
            contentLoadingSwitcher.setup(
                ContentLoadingSettings(
                    contentViews = listOf(contentLayout, checkLayout, reviewRating, addButton),
                    errorViews = listOf(reusableReload.reusableReload),
                    loadingViews = listOf(reusableLoading.progressBar),
                    initState = ContentLoadingState.CONTENT
                )
            )
            reusableReload.reloadButton.setOnClickListener {
                contentLoadingSwitcher.switchState(ContentLoadingState.LOADING, SwitchAnimationParams(delay = 500L))
            }
        }
    }

    override fun initObserves() = Unit

    override fun initListeners() {
        binding.addButton.setOnClickListener(onAddButtonClickListener)
        binding.reviewRating.onRatingBarChangeListener = onRatingBarChangeListener
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private val onAddButtonClickListener = OnClickListener { button ->
        if (binding.reviewRating.rating != 0F) {
            with(binding) {
                val checkFieldContent = checkField.text.toString()
                val reviewActivityResult = ReviewActivityResult(
                    contentField.text.toString(),
                    checkFieldContent.takeIf { it.isNotEmpty() }?.toInt() ?: DEFAULT_CHECK_VALUE,
                    reviewRating.rating
                )
                intent.putExtra(RESULT, reviewActivityResult)
                setResult(RESULT_CODE, intent)
                finish()
            }
        } else {
            Snackbar.make(button, R.string.restaurant_review_leave_a_mark, Snackbar.LENGTH_LONG)
                .setTextColor(resources.getColor(R.color.primary, theme))
                .setAnchorView(button)
                .show()
        }
    }

    private val onRatingBarChangeListener = OnRatingBarChangeListener { ratingBar, _, _ ->
        ratingBar.min = MINIMUM_RATING_BAR_POINTS
    }

    companion object {

        const val DEFAULT_CHECK_VALUE = 0

        const val MINIMUM_RATING_BAR_POINTS = 1

        const val RESULT_CODE = 993

        const val RESTAURANT_NAME = "restaurantName"

        const val RESULT = "reviewActivityResult"

        fun createIntent(context: Context, restaurantName: String): Intent {
            val intent = Intent(context, RestaurantReviewActivity::class.java)
            intent.putExtra(RESTAURANT_NAME, restaurantName)
            return intent
        }
    }
}