package com.example.hrautomation.utils

import retrofit2.Response

fun <T, R> Response<T>.asResult(callback: (T) -> R): Result<R> {
    if (this.isSuccessful) {
        this.body()?.let { result ->
            return Result.success(callback(result))
        }
        return Result.failure(Exception("Ошибка запроса: пустое тело ответа" + code() + ": " + errorBody()))
    } else {
        return Result.failure(Exception("Ошибка запроса: " + code() + ": " + errorBody()))
    }
}

