package com.example.hrautomation.data.repository

import android.util.Log
import com.example.hrautomation.data.api.EmployeesApi
import com.example.hrautomation.data.model.employee.EmployeesResponseToEmployeesMapper
import com.example.hrautomation.data.model.employee.ListEmployeeResponse
import com.example.hrautomation.data.model.employee.ListEmployeeResponseToListEmployeeMapper
import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.domain.model.employees.ColleaguesSortBy
import com.example.hrautomation.domain.model.employees.ListEmployee
import com.example.hrautomation.domain.repository.EmployeesRepository
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class EmployeesRepositoryImpl @Inject constructor(
    private val api: EmployeesApi,
    private val employeesResponseToEmployeesMapper: EmployeesResponseToEmployeesMapper,
    private val listEmployeeResponseToListEmployeeMapper: ListEmployeeResponseToListEmployeeMapper
) :
    EmployeesRepository {

    private var employeesResponse: List<ListEmployeeResponse> = emptyList()

    override suspend fun getEmployeeList(pageNumber: Int, size: Int, sortBy: ColleaguesSortBy): List<ListEmployee> {
        try {
            employeesResponse = api.getEmployeesResponse(pageNumber, size, sortBy.sortBy).body() ?: emptyList()
        } catch (e: IOException) {
            Log.e("exception", "Where Internet?")
        } catch (e: HttpException) {
            Log.e("exception", "Unexpected response")
        }
        return employeesResponse.map { listEmployeeResponseToListEmployeeMapper.convert(it) }
    }

    override suspend fun getEmployee(id: Long): Result<Employee> {
        val employeeResponse = api.getEmployeeResponse(id)
        if (employeeResponse.isSuccessful) {
            employeeResponse.body()?.let {
                return Result.success(employeesResponseToEmployeesMapper.convert(it))
            }
            return Result.failure(Exception("Нет сотрудника в базе"))
        } else {
            return Result.failure(Exception("Ошибка запроса: " + employeeResponse.code() + ": " + employeeResponse.errorBody()))
        }
    }
}