package com.example.hrautomation.presentation.view.faq

import com.example.hrautomation.presentation.model.FaqCategoryItem

fun interface OnFaqCategoryClickListener {
    fun onClick(category: FaqCategoryItem)
}