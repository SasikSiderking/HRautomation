package com.example.hrautomation.presentation.view.restaurants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.CachedCityLatLngRepository
import com.example.hrautomation.domain.repository.RestaurantsRepository
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.restaurants.BuildingItem
import com.example.hrautomation.presentation.model.restaurants.BuildingToBuildingItemMapper
import com.example.hrautomation.presentation.model.restaurants.CityItem
import com.example.hrautomation.presentation.model.restaurants.ListRestaurantItem
import com.example.hrautomation.presentation.view.restaurants.map.MarkerDelegate
import com.example.hrautomation.presentation.view.restaurants.map.RestaurantsMapState
import com.example.hrautomation.utils.tryLaunch
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.cancelChildren
import timber.log.Timber
import javax.inject.Inject

class RestaurantsViewModel @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
    private val restaurantsRepository: RestaurantsRepository,
    private val buildingToBuildingItemMapper: BuildingToBuildingItemMapper,
    private val cachedCityLatLngRepository: CachedCityLatLngRepository
) : BaseViewModel() {

    val data: LiveData<List<BuildingItem>>
        get() = _data
    private val _data: MutableLiveData<List<BuildingItem>> = MutableLiveData()

    val restaurants: LiveData<List<ListRestaurantItem>>
        get() = _restaurants
    private val _restaurants: MutableLiveData<List<ListRestaurantItem>> = MutableLiveData()

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

    fun chooseCity(city: CityItem) {
        val cityLatLng = LatLng(city.lat, city.lng)
        cachedCityLatLngRepository.setLatLng(cityLatLng)
        cachedCityLatLngRepository.setCityId(city.id)

        reload()
    }

    fun chooseBuilding(buildingId: Long, marker: MarkerDelegate) {
        with(_restaurantsMapState) {
            postValue(
                value?.setChosenBuilding(buildingId, marker)
            )
        }
    }

    fun resetChosenBuilding() {
        with(_restaurantsMapState) {
            postValue(
                value?.setChosenBuilding(null, null)
            )
        }
    }

    fun getSingleRestaurantIdInBuildingOrNull(buildingId: Long): Long? {
        val building = data.value?.find { it.id == buildingId && it.restaurants.size == 1 }
        return building?.restaurants?.first()?.id
    }

    private fun loadData() {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                val preferredCityLatLng = cachedCityLatLngRepository.getLatLng() ?: defaultCityLatLng
                val preferredCityId = cachedCityLatLngRepository.getCityId() ?: DEFAULT_CITY_ID
                val listOfBuildings = restaurantsRepository.getBuildingsByCity(preferredCityId)
                val listOfBuildingItems = listOfBuildings.map {
                    buildingToBuildingItemMapper.convert(it)
                }

                _data.postValue(listOfBuildingItems)
                _restaurantsMapState.postValue(RestaurantsMapState(preferredCityLatLng))

                val listRestaurantItems = listOfBuildingItems.flatMap { it.restaurants }
                _restaurants.postValue(listRestaurantItems)
            },
            doOnError = { error ->
                Timber.e(error)
                _exception.postValue(error)
            }
        )
    }

    private companion object {
        val defaultCityLatLng: LatLng = LatLng(56.4884, 84.9480)
        const val DEFAULT_CITY_ID: Long = 1
    }
}