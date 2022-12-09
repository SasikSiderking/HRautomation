package com.example.hrautomation.utils

fun <T, R> Result<T>.asDomain(callback: (T) -> R): Result<R> {
    val data = getOrElse {
        return Result.failure(it)
    }
    return Result.success(callback(data))
}

