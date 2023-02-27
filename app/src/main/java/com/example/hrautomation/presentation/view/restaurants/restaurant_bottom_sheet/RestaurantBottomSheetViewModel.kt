package com.example.hrautomation.presentation.view.restaurants.restaurant_bottom_sheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.CachedBuildingsRepository
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.restaurants.GapItem
import com.example.hrautomation.presentation.model.restaurants.ListRestaurantToListRestaurantItemMapper
import com.example.hrautomation.utils.tryLaunch
import javax.inject.Inject

class RestaurantBottomSheetViewModel @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
    private val cachedBuildingsRepository: CachedBuildingsRepository,
    private val restaurantToListRestaurantItemMapper: ListRestaurantToListRestaurantItemMapper
) : BaseViewModel() {

    val data: LiveData<List<BaseListItem>>
        get() = _data
    private val _data: MutableLiveData<List<BaseListItem>> = MutableLiveData()

    private lateinit var accumulator: List<BaseListItem>

    fun loadData(buildingId: Long) {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                val buildings = cachedBuildingsRepository.getBuilding(buildingId)
                buildings?.let {
                    val restaurantItems = buildings.restaurants.map { restaurantToListRestaurantItemMapper.convert(it) }
                    accumulator = restaurantItems
                    _data.postValue(restaurantItems.take(1).plus(GapItem(Long.MAX_VALUE, restaurantItems.size - 1)))
                }
            }
        )
    }

    fun loadAllData() {
        _data.postValue(accumulator)
    }
}