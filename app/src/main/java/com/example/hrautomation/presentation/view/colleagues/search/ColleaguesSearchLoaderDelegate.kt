package com.example.hrautomation.presentation.view.colleagues.search

import com.example.hrautomation.domain.model.employees.ColleaguesSortBy
import com.example.hrautomation.domain.model.employees.ListEmployee
import com.example.hrautomation.domain.repository.EmployeesRepository
import com.example.hrautomation.presentation.base.search.SearchLoaderDelegate
import com.example.hrautomation.utils.isNullOrEmptyOrBlank
import javax.inject.Inject

class ColleaguesSearchLoaderDelegate @Inject constructor(
    private val employeesRepository: EmployeesRepository
) : SearchLoaderDelegate<List<ListEmployee>> {

    private var persistentResult: List<ListEmployee>? = null

    override suspend fun loadByRequest(searchRequest: String): List<ListEmployee> {
        val workers = getWorkers()
        return if (!searchRequest.isNullOrEmptyOrBlank()) {
            workers.filter { it.username.contains(searchRequest) }
        } else {
            workers
        }
    }

    private suspend fun getWorkers(): List<ListEmployee> {
        if (persistentResult == null) {
            persistentResult = employeesRepository.getEmployeeList(PAGE_NUMBER, PAGE_SIZE, ColleaguesSortBy.NAME)
        }
        return persistentResult!!
    }

    private companion object {
        const val PAGE_SIZE = 100
        const val PAGE_NUMBER = 0
    }
}