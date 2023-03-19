package com.example.hrautomation.presentation.model.restaurants

import java.io.Serializable

data class ReviewDialogResult(
    val content: String,
    val check: Int,
    val rating: Float
) : Serializable
