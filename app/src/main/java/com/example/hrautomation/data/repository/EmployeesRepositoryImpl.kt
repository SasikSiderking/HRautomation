package com.example.hrautomation.data.repository

import android.util.Log
import com.example.hrautomation.data.api.EmployeesApi2
import com.example.hrautomation.data.model.EmployeeResponse
import com.example.hrautomation.data.model.EmployeesResponseToEmployeesMapper
import com.example.hrautomation.domain.model.Employee
import com.example.hrautomation.domain.repository.EmployeesRepository
import com.example.hrautomation.presentation.view.colleagues.SelectedColleagueCacheManager
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class EmployeesRepositoryImpl @Inject constructor(
    private val api: EmployeesApi2,
    private val selectedColleagueCacheManager: SelectedColleagueCacheManager
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
        return employeesResponse.map { EmployeesResponseToEmployeesMapper().convert(it) }
    }

    override fun setSelectedEmployee(employee: Employee) {
        selectedColleagueCacheManager.setSelectedEmployee(employee)
    }

    override fun getSelectedEmployee(): Employee {
        return selectedColleagueCacheManager.getSelectedEmployee()!!
    }
}