package com.example.hrautomation.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Employee(
    val name: String,
    val email: String,
    val project: String,
    val post: String,
    val info: String
)