package com.example.hrautomation.presentation.view.restaurants.restaurant_details

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.hrautomation.R
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityRestaurantDetailsBinding
import com.example.hrautomation.presentation.base.activity.BaseActivity
import com.example.hrautomation.presentation.model.restaurants.ReviewActivityResult
import com.example.hrautomation.utils.ui.switcher.ContentLoadingSettings
import com.example.hrautomation.utils.ui.switcher.ContentLoadingState
import com.example.hrautomation.utils.ui.switcher.base.SwitchAnimationParams

class RestaurantDetailsActivity : BaseActivity<ActivityRestaurantDetailsBinding>() {

    private val selectedRestaurantId: Long by lazy { intent.getLongExtra(ID_EXTRA, 0L) }

    private lateinit var selectedRestaurantName: String

    override val bindingInflater: (LayoutInflater) -> ActivityRestaurantDetailsBinding
        get() = { ActivityRestaurantDetailsBinding.inflate(layoutInflater) }

    private val viewModel: RestaurantDetailsViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var adapter: RestaurantDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.title.text = getString(R.string.restaurant_details_activity_action_bar)

        viewModel.loadData(selectedRestaurantId)
    }

    override fun initInject() {
        (applicationContext as App).appComponent.inject(this)
    }

    override fun initUI() {
        with(binding) {
            contentLoadingSwitcher.setup(
                ContentLoadingSettings(
                    contentViews = listOf(mainContent, addReviewButton),
                    errorViews = listOf(reusableReload.reusableReload),
                    loadingViews = listOf(reusableLoading.progressBar),
                    initState = ContentLoadingState.LOADING
                )
            )
            reusableReload.reloadButton.setOnClickListener {
                viewModel.reload(selectedRestaurantId)
                contentLoadingSwitcher.switchState(ContentLoadingState.LOADING, SwitchAnimationParams(delay = 500L))
            }

            adapter = RestaurantDetailsAdapter()
            reviewsRecyclerView.adapter = adapter
        }
    }

    override fun initObserves() {
        viewModel.state.observe(this, stateObserver)
        viewModel.exception.observe(this, exceptionObserver)
    }

    override fun initListeners() {
        binding.addReviewButton.setOnClickListener(View.OnClickListener {
            activityResultLauncher.launch(RestaurantReviewActivity.createIntent(this, selectedRestaurantName))
        })
        binding.homeAsUp.setOnClickListener(View.OnClickListener {
            with(binding.nestedScrollView) {
                if (scrollY > SCROLL_DEFAULT_POSITION) {
                    smoothScrollTo(SCROLL_DEFAULT_POSITION, SCROLL_DEFAULT_POSITION)
                } else {
                    finish()
                }
            }
        })
        binding.nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > SCROLL_DEFAULT_POSITION && oldScrollY == SCROLL_DEFAULT_POSITION) {
                ObjectAnimator.ofFloat(binding.homeAsUp, "rotation", HOME_UP_LEFT_POSITION, HOME_UP_TOP_POSITION).apply {
                    duration = ANIMATION_DURATION_ROTATE_TOP
                    start()
                }
            } else if (scrollY == SCROLL_DEFAULT_POSITION) {
                ObjectAnimator.ofFloat(binding.homeAsUp, "rotation", HOME_UP_TOP_POSITION, HOME_UP_LEFT_POSITION).apply {
                    duration = ANIMATION_DURATION_ROTATE_LEFT
                    start()
                }
            }
        }
    }

    private var activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        val reviewActivityResult: ReviewActivityResult? =
            result.data?.getSerializableExtra(RestaurantReviewActivity.RESULT) as ReviewActivityResult?
        if (reviewActivityResult != null) {
            viewModel.addReview(
                selectedRestaurantId,
                reviewActivityResult.content,
                reviewActivityResult.check,
                reviewActivityResult.rating
            )
        }
    }

    private val stateObserver = Observer<RestaurantDetailsState> { state ->
        selectedRestaurantName = state.restaurant.name
        val restaurant = state.restaurant
        val reviews = state.reviews

        with(binding) {
            restaurantName.text = getString(R.string.restaurant_item_name, restaurant.name)
            restaurantRating.text = restaurant.rating
            restaurantStatus.text = restaurant.status
            restaurantCheck.text = restaurant.check
            restaurantAddress.text = restaurant.address
            reviewCount.text = getString(R.string.number_of_reviews, reviews.size)
        }
        adapter.update(reviews)

        contentLoadingSwitcher.switchState(ContentLoadingState.CONTENT, SwitchAnimationParams(delay = 500L))
    }

    val exceptionObserver = Observer<Throwable?> { exception ->
        exception?.let {
            contentLoadingSwitcher.switchState(ContentLoadingState.ERROR, SwitchAnimationParams(delay = 500L))
        }
    }

    companion object {

        private const val SCROLL_DEFAULT_POSITION = 0

        private const val HOME_UP_LEFT_POSITION = 0f

        private const val HOME_UP_TOP_POSITION = 90f

        private const val ANIMATION_DURATION_ROTATE_LEFT = 250L

        private const val ANIMATION_DURATION_ROTATE_TOP = 500L

        private const val ID_EXTRA = "selectedItemId"

        fun createIntent(context: Context, id: Long): Intent {
            val intent = Intent(context, RestaurantDetailsActivity::class.java)
            intent.putExtra(ID_EXTRA, id)
            return intent
        }
    }
}