package com.example.hrautomation.presentation.view.social

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.model.social.EventSortBy
import com.example.hrautomation.domain.repository.SocialRepository
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.factory.ItemFactory
import com.example.hrautomation.presentation.model.social.EventFilterParam
import com.example.hrautomation.presentation.model.social.ListEventItem
import com.example.hrautomation.utils.publisher.EventFilterEvent
import com.example.hrautomation.utils.publisher.EventFilterPublisher
import com.example.hrautomation.utils.tryLaunch
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

class SocialViewModel @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
    private val socialRepository: SocialRepository,
    private val itemFactory: ItemFactory,
    eventFilterPublisher: EventFilterPublisher,
) : BaseViewModel() {

    val data: LiveData<List<BaseListItem>>
        get() = _data
    private val _data: MutableLiveData<List<BaseListItem>> = MutableLiveData()

    private lateinit var reservedData: List<ListEventItem>

    init {
        loadData()
        eventFilterPublisher.eventFilterEventFlow
            .onEach { event ->
                when (event) {
                    is EventFilterEvent.ProfileEventFilter -> {
                        filter(event.eventFilterParam)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun reload() {
        viewModelScope.coroutineContext.cancelChildren()
        clearExceptionState()
        loadData()
    }

    private fun filter(filterParam: EventFilterParam) {
        if (filterParam.date != null) {
            reservedData = reservedData.filter { it.date == filterParam.date }
        }
        if (filterParam.format != null) {
            reservedData = reservedData.filter { it.format == filterParam.format }
        }
        if (filterParam.name != null) {
            reservedData = reservedData.filter { it.name.contains(filterParam.name) }
        }
        if (filterParam.cityId != null) {
            reservedData = reservedData.filter { it.name == filterParam.cityId.toString() }
        }
        _data.postValue(reservedData)
    }

    private fun loadData() {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                val events = socialRepository.getAllEvents(PAGE_NUMBER, PAGE_SIZE, EventSortBy.ID)
                val eventItems = itemFactory.createListEventItems(events)

                _data.postValue(eventItems)
                reservedData = eventItems
            },
            doOnError = { error ->
                Timber.e(error)
                _exception.postValue(error)
            }
        )
    }

    private companion object {
        const val PAGE_NUMBER = 0
        const val PAGE_SIZE = 100
    }

}