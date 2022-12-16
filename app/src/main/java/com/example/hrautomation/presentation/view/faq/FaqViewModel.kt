package com.example.hrautomation.presentation.view.faq

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.FaqRepository
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.faq.FaqCategoryToFaqCategoryItemMapper
import com.example.hrautomation.utils.tryLaunch
import kotlinx.coroutines.cancelChildren
import timber.log.Timber
import javax.inject.Inject

class FaqViewModel @Inject constructor(
    private val faqRepo: FaqRepository,
    private val dispatchers: CoroutineDispatchers,
    private val faqCategoryToFaqCategoryItemMapper: FaqCategoryToFaqCategoryItemMapper
) : BaseViewModel() {

    val data: LiveData<List<BaseListItem>>
        get() = _data
    private val _data = MutableLiveData<List<BaseListItem>>()

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
                    _data.postValue(faqRepo.getFaqCategoryList().map { faqCategoryToFaqCategoryItemMapper.convert(it) })
                },
                doOnError = { error ->
                    Timber.e(error)
                    _exception.postValue(error)
                }
            )
    }
}