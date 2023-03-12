package com.example.hrautomation.presentation.view.restaurants.restaurant_details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.hrautomation.R
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityRestaurantDetailsBinding
import com.example.hrautomation.presentation.base.activity.BaseActivity
import com.example.hrautomation.utils.ui.switcher.ContentLoadingSettings
import com.example.hrautomation.utils.ui.switcher.ContentLoadingState
import com.example.hrautomation.utils.ui.switcher.base.SwitchAnimationParams

class RestaurantDetailsActivity : BaseActivity<ActivityRestaurantDetailsBinding>() {

    private val selectedItemId: Long by lazy { intent.getLongExtra(ID_EXTRA, 0L) }

    override val bindingInflater: (LayoutInflater) -> ActivityRestaurantDetailsBinding
        get() = { ActivityRestaurantDetailsBinding.inflate(layoutInflater) }

    private val viewModel: RestaurantDetailsViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var adapter: RestaurantDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.restaurant_details_activity_action_bar)

        viewModel.loadData(selectedItemId)
    }

    override fun initInject() {
        (applicationContext as App).appComponent.inject(this)
    }

    override fun initUI() {
        with(binding) {
            contentLoadingSwitcher.setup(
                ContentLoadingSettings(
                    contentViews = listOf(mainContent),
                    errorViews = listOf(reusableReload.reusableReload),
                    loadingViews = listOf(reusableLoading.progressBar),
                    initState = ContentLoadingState.LOADING
                )
            )
            reusableReload.reloadButton.setOnClickListener {
                viewModel.reload(selectedItemId)
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

    private val stateObserver = Observer<RestaurantDetailsState> { state ->
        val restaurant = state.restaurant
        val reviews = state.reviews

        with(binding) {
            restaurantName.text = restaurant.name
            restaurantRating.text = restaurant.rating
            restaurantStatus.text = restaurant.status
            restaurantCheck.text = restaurant.check
            restaurantAddress.text = restaurant.address
            reviewCount.text = getString(R.string.number_of_reviews, reviews.size)
        }
        adapter.update(reviews)

        contentLoadingSwitcher.switchState(ContentLoadingState.CONTENT, SwitchAnimationParams(delay = 500L))
    }

    override val exceptionObserver = Observer<Throwable?> { exception ->
        exception?.let {
            contentLoadingSwitcher.switchState(ContentLoadingState.ERROR, SwitchAnimationParams(delay = 500L))
        }
    }

    companion object {
        private const val ID_EXTRA = "selectedItemId"

        fun createIntent(context: Context, id: Long): Intent {
            val intent = Intent(context, RestaurantDetailsActivity::class.java)
            intent.putExtra(ID_EXTRA, id)
            return intent
        }
    }
}