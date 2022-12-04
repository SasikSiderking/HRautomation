package com.example.hrautomation.utils.retrofit_adapter

import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

internal class ResultCall<T>(proxy: Call<T>) : CallDelegate<T, Result<T>>(proxy) {

    override fun enqueueImpl(callback: Callback<Result<T>>) {
        proxy.enqueue(ResultCallback(this, callback))
    }

    override fun cloneImpl(): ResultCall<T> {
        return ResultCall(proxy.clone())
    }

    private class ResultCallback<T>(
        private val proxy: ResultCall<T>,
        private val callback: Callback<Result<T>>
    ) : Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            val result: Result<T> = if (response.isSuccessful) {
                if (response.body() == null) {
                    Result.failure(IllegalStateException("Item is null or has a wrong type"))
                } else {
                    Result.success(response.body()) as Result<T>
                }
            } else {
                Result.failure(
                    HttpException(
                        response
                    )
                )
            }
            callback.onResponse(proxy, Response.success(result))
        }

        override fun onFailure(call: Call<T>, error: Throwable) {
            val result: Result<T> = when (error) {
                is HttpException -> Result.failure(error)
                is IOException -> Result.failure(IOException(error.message, error.cause))
                else -> Result.failure(error)
            }
            callback.onResponse(proxy, Response.success(result))
        }
    }

    override fun timeout(): Timeout {
        return proxy.timeout()
    }
}