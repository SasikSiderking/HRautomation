package com.example.hrautomation.presentation.resources

import android.content.res.Resources
import com.example.hrautomation.utils.resources.StringResourceProvider
import javax.inject.Inject

class StringResourceProviderImpl @Inject constructor(private val resources: Resources) : StringResourceProvider {
    override fun getString(resourceId: Int, vararg parameter: Any): String {
        return resources.getString(resourceId, *parameter)
    }
}