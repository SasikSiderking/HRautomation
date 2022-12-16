package com.example.hrautomation.domain.model.employees


data class ListEmployee(
    val id: Long,
    val username: String,
    val post: String,
    val pictureUrl: String?
)