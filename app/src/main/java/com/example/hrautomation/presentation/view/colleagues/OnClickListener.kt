package com.example.hrautomation.presentation.view.colleagues

import com.example.hrautomation.domain.model.Employee

class OnEmployeeClickListener(val clickListener: (employee: Employee) -> Unit) {
    fun onClick(employee: Employee) = clickListener(employee)
}