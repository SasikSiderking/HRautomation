package com.example.hrautomation.utils

fun String?.isNullOrEmptyOrBlank(): Boolean {
    return this == null || this.isEmpty() || this.isBlank()
}