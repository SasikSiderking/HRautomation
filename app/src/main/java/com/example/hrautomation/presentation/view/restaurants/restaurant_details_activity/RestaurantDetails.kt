package com.example.hrautomation.presentation.view.restaurants.restaurant_details_activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.hrautomation.R
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.ActivityRestaurantDetailsBinding
import com.example.hrautomation.presentation.base.activity.DetailedItemActivity
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.model.restaurants.RestaurantItem
import com.example.hrautomation.presentation.model.restaurants.ReviewItem
import com.example.hrautomation.utils.ui.switcher.ContentLoadingSettings
import com.example.hrautomation.utils.ui.switcher.ContentLoadingState
import com.example.hrautomation.utils.ui.switcher.base.SwitchAnimationParams

class RestaurantDetails : DetailedItemActivity<ActivityRestaurantDetailsBinding>() {

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
        viewModel.data.observe(this, selectedItemObserver)
        viewModel.reviews.observe(this, reviewsObserver)
        viewModel.exception.observe(this, exceptionObserver)
    }

    override val selectedItemObserver: Observer<BaseListItem>
        get() = Observer<BaseListItem> { restaurant ->
            val restaurantItem = restaurant as RestaurantItem
            with(binding) {
                restaurantName.text = restaurantItem.name
                restaurantRating.text = restaurantItem.rating
                restaurantStatus.text = restaurantItem.status
                restaurantCheck.text = restaurantItem.check
                restaurantAddress.text = restaurantItem.address
            }

            contentLoadingSwitcher.switchState(ContentLoadingState.CONTENT, SwitchAnimationParams(delay = 500L))
        }

    private val reviewsObserver = Observer<List<ReviewItem>> {
        binding.reviewCount.text = getString(R.string.number_of_reviews, it.size)
        adapter.update(it)
    }

    companion object {
        private const val ID_EXTRA = "selectedItemId"

        fun createIntent(context: Context, id: Long): Intent {
            val intent = Intent(context, RestaurantDetails::class.java)
            intent.putExtra(ID_EXTRA, id)
            return intent
        }
    }
}