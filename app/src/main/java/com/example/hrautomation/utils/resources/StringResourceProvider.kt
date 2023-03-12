package com.example.hrautomation.utils.resources

import androidx.annotation.StringRes

interface StringResourceProvider {
    fun getString(@StringRes resourceId: Int, vararg parameter: Any): String
}