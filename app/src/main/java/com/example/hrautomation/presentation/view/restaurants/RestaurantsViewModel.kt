package com.example.hrautomation.presentation.view.restaurants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.model.restaurants.RestaurantSortBy
import com.example.hrautomation.domain.repository.CachedCityLatLngRepository
import com.example.hrautomation.domain.repository.RestaurantsRepository
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.restaurants.ListRestaurantItem
import com.example.hrautomation.presentation.model.restaurants.ListRestaurantToListRestaurantItemMapper
import com.example.hrautomation.utils.tryLaunch
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelChildren
import timber.log.Timber
import javax.inject.Inject

class RestaurantsViewModel @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
    private val restaurantsRepository: RestaurantsRepository,
    private val listRestaurantToListRestaurantItemMapper: ListRestaurantToListRestaurantItemMapper,
    private val cachedCityLatLngRepository: CachedCityLatLngRepository
) : BaseViewModel() {

    val data: LiveData<List<ListRestaurantItem>>
        get() = _data
    private val _data: MutableLiveData<List<ListRestaurantItem>> = MutableLiveData()

    val restaurantsMapState: LiveData<RestaurantsMapState>
        get() = _restaurantsMapState
    private val _restaurantsMapState: MutableLiveData<RestaurantsMapState> = MutableLiveData()

    init {
        loadData()
    }

    fun reload() {
        viewModelScope.coroutineContext.cancelChildren()
        clearExceptionState()
        loadData()
    }

    fun chooseCity(cityLatLng: LatLng) {
        cachedCityLatLngRepository.setLatLng(cityLatLng)

        with(_restaurantsMapState) {
            postValue(
                value?.setCurrentCity(cityLatLng)
            )
        }
    }

    fun chooseRestaurant(restaurantId: Long, marker: Marker) {
        with(_restaurantsMapState) {
            postValue(
                value?.setChosenRestaurant(restaurantId, marker)
            )
        }
    }

    fun resetChosenRestaurant() {
        with(_restaurantsMapState) {
            postValue(
                value?.setChosenRestaurant(null, null)
            )
        }
    }

    private fun getPreferredCityLatLng(): LatLng {
        return cachedCityLatLngRepository.getLatLng() ?: defaultCityLatLng
    }

    private fun loadData() {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                val listOfRestaurantsDeferred = async(dispatchers.io) {
                    restaurantsRepository.getRestaurantList(
                        PAGE_NUMBER,
                        PAGE_SIZE,
                        RestaurantSortBy.NAME
                    ).map {
                        listRestaurantToListRestaurantItemMapper.convert(it)
                    }
                }
                val preferredCityLatLngDeferred = async(dispatchers.io) {
                    cachedCityLatLngRepository.getLatLng() ?: defaultCityLatLng
                }

                val listOfRestaurantItems = listOfRestaurantsDeferred.await()
                val preferredCityLatLng = preferredCityLatLngDeferred.await()

                _data.postValue(listOfRestaurantItems)
                _restaurantsMapState.postValue(RestaurantsMapState(preferredCityLatLng))
            },
            doOnError = { error ->
                Timber.e(error)
                _exception.postValue(error)
            }
        )
    }

    private companion object {
        const val PAGE_SIZE = 100
        const val PAGE_NUMBER = 0
        val defaultCityLatLng: LatLng = LatLng(56.4884, 84.9480)
    }
}