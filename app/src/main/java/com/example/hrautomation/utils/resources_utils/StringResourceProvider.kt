package com.example.hrautomation.utils.resources_utils

import androidx.annotation.StringRes

interface StringResourceProvider {
    fun getString(@StringRes resourceId: Int, vararg parameter: Any): String
}