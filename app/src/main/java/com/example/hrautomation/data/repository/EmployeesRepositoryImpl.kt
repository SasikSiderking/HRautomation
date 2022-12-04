package com.example.hrautomation.data.repository

import com.example.hrautomation.data.api.EmployeesApi
import com.example.hrautomation.data.model.employee.EmployeesResponseToEmployeesMapper
import com.example.hrautomation.data.model.employee.ListEmployeeResponse
import com.example.hrautomation.data.model.employee.ListEmployeeResponseToListEmployeeMapper
import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.domain.model.employees.ColleaguesSortBy
import com.example.hrautomation.domain.model.employees.ListEmployee
import com.example.hrautomation.domain.repository.EmployeesRepository
import com.example.hrautomation.utils.asDomain
import javax.inject.Inject

class EmployeesRepositoryImpl @Inject constructor(
    private val api: EmployeesApi,
    private val employeesResponseToEmployeesMapper: EmployeesResponseToEmployeesMapper,
    private val listEmployeeResponseToListEmployeeMapper: ListEmployeeResponseToListEmployeeMapper
) :
    EmployeesRepository {

    private var employeesResponse: List<ListEmployeeResponse> = emptyList()

    override suspend fun getEmployeeList(pageNumber: Int, size: Int, sortBy: ColleaguesSortBy): Result<List<ListEmployee>> {
        return api.getEmployeesResponse(pageNumber, size, sortBy.sortBy).asDomain { employeeList ->
            employeeList.map { listEmployeeResponseToListEmployeeMapper.convert(it) }
        }
    }

    override suspend fun getEmployee(id: Long): Result<Employee> {
        return api.getEmployeeResponse(id).asDomain { employeeResponse ->
            employeesResponseToEmployeesMapper.convert(employeeResponse)
        }
    }
}