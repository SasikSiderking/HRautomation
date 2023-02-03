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
import javax.inject.Inject

class RestaurantsViewModel @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
    private val restaurantsRepository: RestaurantsRepository,
    private val listRestaurantToListRestaurantItemMapper: ListRestaurantToListRestaurantItemMapper
) : BaseViewModel() {

    val data: LiveData<List<ListRestaurantItem>>
        get() = _data
    private val _data: MutableLiveData<List<ListRestaurantItem>> = MutableLiveData<List<ListRestaurantItem>>()

    val chosenRestaurant: LiveData<ListRestaurantItem?>
        get() = _chosenRestaurant
    private val _chosenRestaurant: MutableLiveData<ListRestaurantItem?> = MutableLiveData<ListRestaurantItem?>()

    init {
        loadData()
    }

    fun choseRestaurant(restaurant: ListRestaurantItem?) {
        _chosenRestaurant.postValue(restaurant)
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