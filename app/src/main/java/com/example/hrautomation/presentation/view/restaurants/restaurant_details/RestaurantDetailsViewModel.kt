package com.example.hrautomation.presentation.view.restaurants.restaurant_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.RestaurantsRepository
import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.restaurants.RestaurantToRestaurantItemMapper
import com.example.hrautomation.presentation.model.restaurants.ReviewToReviewItemMapper
import com.example.hrautomation.utils.tryLaunch
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelChildren
import timber.log.Timber
import javax.inject.Inject

class RestaurantDetailsViewModel @Inject constructor(
    private val restaurantsRepository: RestaurantsRepository,
    private val tokenRepository: TokenRepository,
    private val dispatchers: CoroutineDispatchers,
    private val restaurantToRestaurantItemMapper: RestaurantToRestaurantItemMapper,
    private val reviewToReviewItemMapper: ReviewToReviewItemMapper
) : BaseViewModel() {

    val state: LiveData<RestaurantDetailsState>
        get() = _state
    private val _state = MutableLiveData<RestaurantDetailsState>()

    fun reload(id: Long) {
        viewModelScope.coroutineContext.cancelChildren()
        clearExceptionState()
        loadData(id)
    }

    fun loadData(restaurantId: Long) {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                val restaurantDeferred = async(dispatchers.io) {
                    restaurantToRestaurantItemMapper.convert(
                        restaurantsRepository.getRestaurantById(restaurantId)
                    )
                }
                val reviewsDeferred = async(dispatchers.io) {
                    restaurantsRepository.getReviewsByRestaurantId(restaurantId).map {
                        reviewToReviewItemMapper.convert(it)
                    }
                }

                val restaurant = restaurantDeferred.await()
                val reviews = reviewsDeferred.await()

                _state.postValue(RestaurantDetailsState(restaurant, reviews))
            },
            doOnError = { error ->
                Timber.e(error)
                _exception.postValue(error)
            }
        )
    }

    fun addReview(restaurantId: Long, content: String, check: Int, rating: Float) {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                tokenRepository.getUserId()?.let {
                    restaurantsRepository.addReview(restaurantId, it, content, check, rating)
                }
                loadData(state.value!!.restaurant.id)
            },
            doOnError = { error ->
                Timber.e(error)
                _exception.postValue(error)
            }
        )
    }

}