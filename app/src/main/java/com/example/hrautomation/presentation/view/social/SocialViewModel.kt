package com.example.hrautomation.presentation.view.social

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.model.social.EventSortBy
import com.example.hrautomation.domain.repository.SocialRepository
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.social.ListEventItem
import com.example.hrautomation.utils.tryLaunch
import kotlinx.coroutines.cancelChildren
import timber.log.Timber
import javax.inject.Inject

class SocialViewModel @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
    private val socialRepository: SocialRepository,
) : BaseViewModel() {

    val data: LiveData<List<BaseListItem>>
        get() = _data
    private val _data: MutableLiveData<List<BaseListItem>> = MutableLiveData()

    init {
        loadData()
    }

    fun reload() {
        viewModelScope.coroutineContext.cancelChildren()
        clearExceptionState()
        loadData()
    }

    private fun loadData() {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                val events = socialRepository.getAllEvents(PAGE_NUMBER, PAGE_SIZE, EventSortBy.ID)
                val eventItems = events.map { ListEventItem.createFrom(it) }

                _data.postValue(eventItems)
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