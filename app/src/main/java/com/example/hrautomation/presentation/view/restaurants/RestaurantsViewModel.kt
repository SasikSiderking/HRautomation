package com.example.hrautomation.presentation.view.restaurants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.model.restaurants.RestaurantSortBy
import com.example.hrautomation.domain.repository.RestaurantsRepository
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.restaurants.ListRestaurantItem
import com.example.hrautomation.presentation.model.restaurants.ListRestaurantToListRestaurantItemMapper
import com.example.hrautomation.utils.tryLaunch
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import javax.inject.Inject

class RestaurantsViewModel @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
    private val restaurantsRepository: RestaurantsRepository,
    private val listRestaurantToListRestaurantItemMapper: ListRestaurantToListRestaurantItemMapper
) : BaseViewModel() {

    val data: LiveData<List<ListRestaurantItem>>
        get() = _data
    private val _data: MutableLiveData<List<ListRestaurantItem>> = MutableLiveData()

    val restaurantsMapState: LiveData<RestaurantsMapState>
        get() = _restaurantMapState
    private val _restaurantMapState: MutableLiveData<RestaurantsMapState> =
        MutableLiveData(RestaurantsMapState(null, null, null))

    init {
        loadData()
    }

    fun chooseCity(cityLatLng: LatLng?) {
        with(restaurantsMapState) {
            _restaurantMapState.postValue(
                value?.setCurrentCity(cityLatLng)
            )
        }
    }

    fun chooseRestaurant(restaurantId: Long?, marker: Marker?) {
        with(restaurantsMapState) {
            _restaurantMapState.postValue(
                value?.setChosenRestaurant(restaurantId, marker)
            )
        }
    }

    private fun loadData() {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                val listOfRestaurants = restaurantsRepository.getRestaurantList(
                    PAGE_NUMBER,
                    PAGE_SIZE,
                    RestaurantSortBy.NAME
                )
                val listOfRestaurantItems = listOfRestaurants.map {
                    listRestaurantToListRestaurantItemMapper.convert(it)
                }
                _data.postValue(listOfRestaurantItems)
            }
        )
    }

    private companion object {
        const val PAGE_SIZE = 100
        const val PAGE_NUMBER = 0
    }
}