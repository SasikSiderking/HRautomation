package com.example.hrautomation.presentation.view.colleagues

import com.example.hrautomation.presentation.model.colleagues.ListedColleagueItem

fun interface OnColleagueClickListener {
    fun onClick(colleague: ListedColleagueItem)
}