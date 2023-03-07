package com.example.hrautomation.presentation.view.restaurants.restaurant_details_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.RestaurantsRepository
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.restaurants.RestaurantItem
import com.example.hrautomation.presentation.model.restaurants.RestaurantToRestaurantItemMapper
import com.example.hrautomation.presentation.model.restaurants.ReviewItem
import com.example.hrautomation.presentation.model.restaurants.ReviewToReviewItemMapper
import com.example.hrautomation.utils.tryLaunch
import kotlinx.coroutines.cancelChildren
import timber.log.Timber
import javax.inject.Inject

class RestaurantDetailsViewModel @Inject constructor(
    private val restaurantsRepository: RestaurantsRepository,
    private val dispatchers: CoroutineDispatchers,
    private val restaurantToRestaurantItemMapper: RestaurantToRestaurantItemMapper,
    private val reviewToReviewItemMapper: ReviewToReviewItemMapper
) : BaseViewModel() {

    val data: LiveData<RestaurantItem>
        get() = _data
    private val _data = MutableLiveData<RestaurantItem>()

    val reviews: LiveData<List<ReviewItem>>
        get() = _reviews
    private val _reviews = MutableLiveData<List<ReviewItem>>()

    fun reload(id: Long) {
        viewModelScope.coroutineContext.cancelChildren()
        clearExceptionState()
        loadData(id)
    }

    fun loadData(restaurantId: Long) {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                _data.postValue(restaurantToRestaurantItemMapper.convert(restaurantsRepository.getRestaurantById(restaurantId)))
                _reviews.postValue(
                    restaurantsRepository.getReviewsByRestaurantId(restaurantId)
                        .map { reviewToReviewItemMapper.convert(it) })
            },
            doOnError = { error ->
                Timber.e(error)
                _exception.postValue(error)
            }
        )
    }

}