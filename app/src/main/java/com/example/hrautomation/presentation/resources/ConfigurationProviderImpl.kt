package com.example.hrautomation.presentation.resources

import android.content.res.Configuration
import android.content.res.Resources
import com.example.hrautomation.utils.resources.ConfigurationProvider
import javax.inject.Inject

class ConfigurationProviderImpl @Inject constructor(private val resources: Resources) : ConfigurationProvider {
    override fun getConfiguration(): Configuration {
        return resources.configuration
    }
}