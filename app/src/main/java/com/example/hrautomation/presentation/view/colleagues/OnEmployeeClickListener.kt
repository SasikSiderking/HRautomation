package com.example.hrautomation.presentation.view.colleagues

import com.example.hrautomation.domain.model.Employee

fun interface OnEmployeeClickListener {
    fun onClick(employee: Employee)
}