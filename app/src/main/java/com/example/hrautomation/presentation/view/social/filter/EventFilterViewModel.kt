package com.example.hrautomation.presentation.view.social.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.restaurants.CityItem
import com.example.hrautomation.presentation.model.social.EventFilterParam
import com.example.hrautomation.presentation.model.social.EventFormat
import com.example.hrautomation.utils.publisher.EventFilterEvent
import com.example.hrautomation.utils.publisher.EventFilterPublisher
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class EventFilterViewModel @Inject constructor(private val eventFilterPublisher: EventFilterPublisher) : BaseViewModel() {
    val eventFilterParam: LiveData<EventFilterParam>
        get() = _eventFilterParam
    private val _eventFilterParam: MutableLiveData<EventFilterParam> =
        MutableLiveData(EventFilterParamGlobal.eventFilterParam)


    fun setFromDateFilter(date: Date?) {
        _eventFilterParam.postValue(eventFilterParam.value?.copy(fromDate = date))
    }

    fun setToDateFilter(date: Date?) {
        _eventFilterParam.postValue(eventFilterParam.value?.copy(toDate = date))
    }

    fun setNameFilter(name: String?) {
        _eventFilterParam.postValue(eventFilterParam.value?.copy(name = name))
    }

    fun setCityFilter(city: CityItem?) {
        _eventFilterParam.postValue(eventFilterParam.value?.copy(city = city))
    }

    fun setFormatFilter(format: EventFormat?) {
        _eventFilterParam.postValue(eventFilterParam.value?.copy(format = format))
    }

    fun sendFilterParam() {
        viewModelScope.launch {
            Timber.e("name: " + eventFilterParam.value!!.name)
            Timber.e("fromDate: " + eventFilterParam.value!!.fromDate.toString())
            Timber.e("toDate: " + eventFilterParam.value!!.toDate.toString())
            Timber.e("city: " + eventFilterParam.value!!.city.toString())
            Timber.e("format: " + eventFilterParam.value!!.format.toString())
            EventFilterParamGlobal.eventFilterParam = eventFilterParam.value!!
            eventFilterPublisher.emitEvent(EventFilterEvent.ProfileEventFilter(eventFilterParam.value!!))
        }
    }
}