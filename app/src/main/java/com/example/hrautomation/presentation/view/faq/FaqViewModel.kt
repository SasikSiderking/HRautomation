package com.example.hrautomation.presentation.view.faq

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.model.FaqCategory
import com.example.hrautomation.domain.repository.FaqRepository
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.model.FaqCategoryToFaqCategoryItemMapper
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class FaqViewModel @Inject constructor(
    private val faqRepo: FaqRepository,
    private val dispatchers: CoroutineDispatchers,
    private val faqCategoryToFaqCategoryItemMapper: FaqCategoryToFaqCategoryItemMapper
) : ViewModel() {

    val data: LiveData<List<BaseListItem>>
        get() = _data
    private val _data = MutableLiveData<List<BaseListItem>>(emptyList())

    val exception: LiveData<Throwable?>
        get() = _exception
    private var _exception = MutableLiveData<Throwable?>()

    fun clearExceptionState() {
        _exception = MutableLiveData<Throwable?>()
    }

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(dispatchers.io) {
            val result = faqRepo.getFaqCategoryList()
            result.onSuccess { listFaqCategory: List<FaqCategory> ->
                _data.postValue(listFaqCategory.map { faqCategoryToFaqCategoryItemMapper.convert(it) })
            }
            result.onFailure { exception: Throwable ->
                Timber.e(exception)
                _exception.postValue(exception)
            }
        }
    }
}