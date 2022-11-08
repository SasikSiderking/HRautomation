package com.example.hrautomation.utils

sealed class Result<T>(val data: T? = null, val errMsg: String? = null) {
    class Success<T>(data: T?) : Result<T>(data)
    class Error<T>(errMsg: String, data: T? = null) : Result<T>(data, errMsg)
}