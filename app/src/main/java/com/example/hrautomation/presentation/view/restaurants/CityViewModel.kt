package com.example.hrautomation.presentation.view.restaurants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.model.restaurants.City
import com.example.hrautomation.domain.repository.RestaurantsRepository
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.restaurants.CityToCityItemMapper
import com.example.hrautomation.utils.tryLaunch
import timber.log.Timber
import javax.inject.Inject

class CityViewModel @Inject constructor(
    private val restaurantsRepository: RestaurantsRepository,
    private val dispatchers: CoroutineDispatchers,
    private val cityToCityItemMapper: CityToCityItemMapper
) : BaseViewModel() {

    val data: LiveData<List<BaseListItem>>
        get() = _data
    private val _data = MutableLiveData<List<BaseListItem>>()

    private var reservedData: List<City> = emptyList()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                reservedData = restaurantsRepository.getCitiesResponse()
                _data.postValue(reservedData.map { cityToCityItemMapper.convert(it) })
            },
            doOnError = { error ->
                Timber.e(error)
                _exception.postValue(error)
            }
        )
    }

    fun reload() {
        clearExceptionState()
        loadData()
    }

    fun performSearch(name: String) {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.default,
            doOnLaunch = {
                if (name.isNotEmpty()) {
                    val citiesDomain = reservedData.filter { city ->
                        city.name.contains(name, ignoreCase = true)
                    }
                    val cities = citiesDomain.map { cityToCityItemMapper.convert(it) }

                    _data.postValue(cities)
                } else {
                    if (reservedData.isNotEmpty()) {
                        _data.postValue(reservedData.map { cityToCityItemMapper.convert(it) })
                    }
                }
            },
            doOnError = { error ->
                Timber.e(error)
                _exception.postValue(error)
            }
        )
    }
}