package com.example.hrautomation.presentation.view.restaurants.restaurant_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.R
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.model.restaurants.RestaurantReviewRequest
import com.example.hrautomation.domain.repository.RestaurantsRepository
import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.restaurants.RestaurantToRestaurantItemMapper
import com.example.hrautomation.presentation.model.restaurants.ReviewToReviewItemMapper
import com.example.hrautomation.utils.resources.StringResourceProvider
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
    private val reviewToReviewItemMapper: ReviewToReviewItemMapper,
    private val stringResourceProvider: StringResourceProvider
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
                val restaurantReviewRequest = prepareReviewRequest(restaurantId, content, check, rating)
                if (restaurantReviewRequest != null) {
                    restaurantsRepository.addReview(restaurantReviewRequest)
                    loadData(restaurantId)
                } else {
                    throw IllegalStateException(stringResourceProvider.getString(R.string.error_user_id_is_null))
                }
            },
            doOnError = { error ->
                Timber.e(error)
                _exception.postValue(error)
            }
        )
    }

    private fun prepareReviewRequest(
        restaurantId: Long,
        content: String,
        check: Int,
        rating: Float
    ): RestaurantReviewRequest? {
        val userId = tokenRepository.getUserId()
        return if (userId != null) {
            RestaurantReviewRequest(userId, restaurantId, content, check, rating)
        } else {
            null
        }
    }

}