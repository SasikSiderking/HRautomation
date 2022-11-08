package com.example.hrautomation.data.repository

import android.util.Log
import com.example.hrautomation.data.api.EmployeesApi2
import com.example.hrautomation.data.model.EmployeeResponse
import com.example.hrautomation.data.model.EmployeesResponseToEmployeesMapper
import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.domain.repository.EmployeesRepository
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class EmployeesRepositoryImpl @Inject constructor(
    private val api: EmployeesApi2,
    private val employeesResponseToEmployeesMapper: EmployeesResponseToEmployeesMapper
) :
    EmployeesRepository {

    private var employeesResponse: List<EmployeeResponse> = emptyList()

    override suspend fun getEmployeeList(): List<Employee> {
        if (employeesResponse.isEmpty()) {
            try {
                employeesResponse = api.getEmployeesResponse().body() ?: emptyList()
            } catch (e: IOException) {
                Log.e("exception", "Where Internet?")
            } catch (e: HttpException) {
                Log.e("exception", "Unexpected response")
            }
        }
        return employeesResponse.map { employeesResponseToEmployeesMapper.convert(it) }
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