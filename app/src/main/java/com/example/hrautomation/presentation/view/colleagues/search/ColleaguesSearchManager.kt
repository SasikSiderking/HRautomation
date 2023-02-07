package com.example.hrautomation.presentation.view.colleagues.search

import com.example.hrautomation.databinding.ItemEmployeeBinding
import com.example.hrautomation.domain.model.employees.ListEmployee
import com.example.hrautomation.presentation.base.search.SearchManager
import com.example.hrautomation.presentation.model.colleagues.EmployeeToColleagueItemMapper
import com.example.hrautomation.presentation.model.colleagues.ListedColleagueItem
import javax.inject.Inject

class ColleaguesSearchManager @Inject constructor(
    private val colleaguesLoaderDelegate: ColleaguesSearchLoaderDelegate,
    private val employeeToColleagueItemMapper: EmployeeToColleagueItemMapper,
) : SearchManager<ListedColleagueItem, ItemEmployeeBinding, List<ListEmployee>>(
    ColleaguesSearchUiDelegate(),
    colleaguesLoaderDelegate
) {

    private var onLoadingStartedListener: (() -> Unit)? = null
    private var onLoadingFinishedListener: (() -> Unit)? = null

    fun applyDefaultSearchResult() {
        processSearch(EMPTY_REQUEST)
    }

    override fun convertToItems(data: List<ListEmployee>): List<ListedColleagueItem> {
        return data.map { employeeToColleagueItemMapper.convert(it) }
    }

    override fun onLoadingStarted() {
        onLoadingStartedListener?.invoke()
    }

    override fun onLoadingFinished() {
        onLoadingFinishedListener?.invoke()
    }

    fun setOnResultClickListener(listener: ((Long) -> Unit)?) {
        (uiDelegate as ColleaguesSearchUiDelegate).setOnResultClickListener(listener)
    }

    fun setOnLoadingStartedListener(listener: (() -> Unit)?) {
        this.onLoadingStartedListener = listener
    }

    fun setOnLoadingFinishedListener(listener: (() -> Unit)?) {
        this.onLoadingFinishedListener = listener
    }

    private companion object {
        const val EMPTY_REQUEST = ""
    }
}