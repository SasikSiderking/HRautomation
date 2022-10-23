package com.example.hrautomation.domain.repository

import com.example.hrautomation.domain.model.Employee

interface IEmployeesRepository {
    suspend fun getEmployeeList(): List<Employee>
}