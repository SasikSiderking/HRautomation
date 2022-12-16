package com.example.hrautomation.data.repository

import com.example.hrautomation.data.api.EmployeesApi
import com.example.hrautomation.data.model.employee.EmployeesResponseToEmployeesMapper
import com.example.hrautomation.data.model.employee.ListEmployeeResponseToListEmployeeMapper
import com.example.hrautomation.domain.model.employees.ColleaguesSortBy
import com.example.hrautomation.domain.model.employees.Employee
import com.example.hrautomation.domain.model.employees.ListEmployee
import com.example.hrautomation.domain.repository.EmployeesRepository
import javax.inject.Inject

class EmployeesRepositoryImpl @Inject constructor(
    private val api: EmployeesApi,
    private val employeesResponseToEmployeesMapper: EmployeesResponseToEmployeesMapper,
    private val listEmployeeResponseToListEmployeeMapper: ListEmployeeResponseToListEmployeeMapper
) :
    EmployeesRepository {

    override suspend fun getEmployeeList(pageNumber: Int, size: Int, sortBy: ColleaguesSortBy): List<ListEmployee> {
        return api.getEmployeesResponse(pageNumber, size, sortBy.sortBy).users.map { listEmployeeResponseToListEmployeeMapper.convert(it) }
    }

    override suspend fun getEmployee(id: Long): Employee {
        return employeesResponseToEmployeesMapper.convert(api.getEmployeeResponse(id))
    }
}