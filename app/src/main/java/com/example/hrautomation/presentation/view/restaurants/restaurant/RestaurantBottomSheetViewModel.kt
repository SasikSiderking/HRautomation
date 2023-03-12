package com.example.hrautomation.presentation.view.restaurants.restaurant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.R
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.RestaurantsRepository
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.restaurants.ListRestaurantToListRestaurantItemMapper
import com.example.hrautomation.presentation.model.restaurants.RestaurantsBottomSheetGapItem
import com.example.hrautomation.utils.resources.StringResourceProvider
import com.example.hrautomation.utils.tryLaunch
import javax.inject.Inject

class RestaurantBottomSheetViewModel @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
    private val restaurantsRepository: RestaurantsRepository,
    private val stringResourceProvider: StringResourceProvider,
    private val restaurantToListRestaurantItemMapper: ListRestaurantToListRestaurantItemMapper
) : BaseViewModel() {

    val data: LiveData<List<BaseListItem>>
        get() = _data
    private val _data: MutableLiveData<List<BaseListItem>> = MutableLiveData()

    private var accumulator: List<BaseListItem> = emptyList()

    fun loadData(buildingId: Long) {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                val building = restaurantsRepository.getBuildingById(buildingId)
                building?.let {
                    val restaurantItems = building.restaurants.map { restaurantToListRestaurantItemMapper.convert(it) }
                    accumulator = restaurantItems
                    _data.postValue(
                        enrichWithGapItem(restaurantItems)
                    )
                }
            }
        )
    }

    private fun enrichWithGapItem(restaurantItems: List<BaseListItem>): List<BaseListItem> {
        val gapItemText = stringResourceProvider.getString(R.string.restaurants_gap_item_text, restaurantItems.size - 1)
        return restaurantItems.take(INITIAL_NUMBER_OF_ITEMS_SHOWN)
            .plus(RestaurantsBottomSheetGapItem(Long.MAX_VALUE, gapItemText))
    }

    fun loadAllData() {
        _data.postValue(accumulator)
    }

    companion object {
        const val INITIAL_NUMBER_OF_ITEMS_SHOWN = 1
    }
}