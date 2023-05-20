package com.example.hrautomation.presentation.view.social

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.model.social.list_event.EventSortBy
import com.example.hrautomation.domain.repository.SocialRepository
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.factory.EventItemFactory
import com.example.hrautomation.presentation.model.social.filter.EventFilterParam
import com.example.hrautomation.presentation.model.social.filter.toFilter
import com.example.hrautomation.presentation.model.social.list_event.ListEventItem
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
    private val eventItemFactory: EventItemFactory,
    private val eventFilterPublisher: EventFilterPublisher,
) : BaseViewModel() {

    val data: LiveData<List<BaseListItem>>
        get() = _data
    private val _data: MutableLiveData<List<BaseListItem>> = MutableLiveData()

    private lateinit var reservedData: List<ListEventItem>

    private var eventFilterParam = EventFilterParam(null, null, null, null, null)

    init {
        loadData(eventFilterParam)
        subscribeForFilterPublisher()
    }

    fun reload() {
        viewModelScope.coroutineContext.cancelChildren()
        clearExceptionState()
        loadData(eventFilterParam)
    }

    private fun loadData(eventFilterParam: EventFilterParam) {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                val events =
                    socialRepository.getAllEvents(PAGE_NUMBER, PAGE_SIZE, EventSortBy.ID, eventFilterParam.toFilter())
                val eventItems = eventItemFactory.createListEventItems(events)

                _data.postValue(eventItems)
                reservedData = eventItems
            },
            doOnError = { error ->
                Timber.e(error)
                _exception.postValue(error)
            }
        )
    }

    private fun subscribeForFilterPublisher() {
        eventFilterPublisher.eventFilterEventFlow
            .onEach { event ->
                when (event) {
                    is EventFilterEvent.ProfileEventFilter -> {
                        eventFilterParam = event.eventFilterParam
                        loadData(eventFilterParam)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private companion object {
        const val PAGE_NUMBER = 0
        const val PAGE_SIZE = 100
    }

}