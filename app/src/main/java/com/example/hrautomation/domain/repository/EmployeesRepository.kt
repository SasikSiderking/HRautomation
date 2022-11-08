package com.example.hrautomation.domain.repository

import com.example.hrautomation.domain.model.Employee

interface EmployeesRepository {
    suspend fun getEmployeeList(): List<Employee>

    suspend fun getEmployee(id: Long): Result<Employee>
}