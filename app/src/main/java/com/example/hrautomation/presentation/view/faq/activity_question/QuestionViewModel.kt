package com.example.hrautomation.presentation.view.faq.activity_question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.FaqRepository
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.model.FaqQuestionToFaqQuestionItemMapper
import com.example.hrautomation.utils.tryLaunch
import timber.log.Timber
import javax.inject.Inject

class QuestionViewModel @Inject constructor(
    private val faqRepo: FaqRepository,
    private val dispatchers: CoroutineDispatchers,
    private val faqQuestionToFaqQuestionItemMapper: FaqQuestionToFaqQuestionItemMapper
) : ViewModel() {

    val data: LiveData<List<BaseListItem>>
        get() = _data
    private val _data = MutableLiveData<List<BaseListItem>>()

    val exception: LiveData<Throwable?>
        get() = _exception
    private val _exception = MutableLiveData<Throwable?>()

    fun clearExceptionState() {
        _exception.postValue(null)
    }

    fun reload(id: Long) {
        loadData(id)
    }

    fun loadData(id: Long) {

        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                _data.postValue(faqRepo.getFaqQuestionList(id).map { faqQuestionToFaqQuestionItemMapper.convert(it) })
            },
            doOnError = { error ->
                Timber.e(error)
                _exception.postValue(error)
            }
        )
    }
}