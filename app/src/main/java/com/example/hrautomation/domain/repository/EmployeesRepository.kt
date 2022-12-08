package com.example.hrautomation.domain.repository

import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.domain.model.employees.ColleaguesSortBy
import com.example.hrautomation.domain.model.employees.ListEmployee

interface EmployeesRepository {
    suspend fun getEmployeeList(pageNumber: Int, size: Int, sortBy: ColleaguesSortBy): List<ListEmployee>

    suspend fun getEmployee(id: Long): Employee
}