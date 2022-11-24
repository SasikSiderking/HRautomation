package com.example.hrautomation.presentation.view.faq.activity_question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.model.FaqQuestion
import com.example.hrautomation.domain.repository.FaqRepository
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.model.FaqQuestionToFaqQuestionItemMapper
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class QuestionViewModel @Inject constructor(
    private val faqRepo: FaqRepository,
    private val dispatchers: CoroutineDispatchers,
    private val faqQuestionToFaqQuestionItemMapper: FaqQuestionToFaqQuestionItemMapper
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

    fun loadData(id: Long) {
        viewModelScope.launch(dispatchers.io) {
            val result = faqRepo.getFaqQuestionList(id)
            result.onSuccess { listFaqQuestion: List<FaqQuestion> ->
                _data.postValue(listFaqQuestion.map { faqQuestionToFaqQuestionItemMapper.convert(it) })
            }.onFailure { exception: Throwable ->
                Timber.e(exception)
                _exception.postValue(exception)
            }
        }
    }
}