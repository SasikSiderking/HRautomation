package com.example.hrautomation.presentation.view.colleagues

import com.example.hrautomation.presentation.model.ColleagueItem

fun interface OnColleagueClickListener {
    fun onClick(colleague: ColleagueItem)
}