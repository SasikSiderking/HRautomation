package com.example.hrautomation.presentation.view.social.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.SocialRepository
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.factory.EventItemFactory
import com.example.hrautomation.presentation.model.social.event.EventItem
import com.example.hrautomation.utils.tryLaunch
import timber.log.Timber
import javax.inject.Inject

class EventDetailsViewModel @Inject constructor(
    private val socialRepository: SocialRepository,
    private val dispatchers: CoroutineDispatchers,
    private val eventItemFactory: EventItemFactory
) : BaseViewModel() {
    val data: LiveData<EventItem>
        get() = _data
    private val _data: MutableLiveData<EventItem> = MutableLiveData()

    fun loadData(id: Long) {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                val event = socialRepository.getEventById(id)
                val eventItem = eventItemFactory.createEventItem(event)
                _data.postValue(eventItem)
            },
            doOnError = { error ->
                Timber.e(error)
                _exception.postValue(error)
            }
        )
    }

    fun reload(eventId: Long) {
        loadData(eventId)
    }
}