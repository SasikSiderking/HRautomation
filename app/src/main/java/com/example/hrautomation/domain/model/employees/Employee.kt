package com.example.hrautomation.domain.model.employees

data class Employee(
    val id: Long,
    val name: String,
    val email: String,
    val project: String?,
    val post: String?,
    val info: String?,
    val pictureUrl: String?
)

