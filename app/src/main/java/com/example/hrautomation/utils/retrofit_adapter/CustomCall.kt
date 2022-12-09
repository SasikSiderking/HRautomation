package com.example.hrautomation.utils.retrofit_adapter

import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class CustomCall<T>(proxy: Call<T>) : CallDelegate<T, T>(proxy) {

    override fun enqueueImpl(callback: Callback<T>) {
        proxy.enqueue(CustomCallback(this, callback))
    }

    override fun cloneImpl(): CustomCall<T> {
        return CustomCall(proxy.clone())
    }

    private class CustomCallback<T>(
        private val proxy: CustomCall<T>,
        private val callback: Callback<T>
    ) : Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                callback.onResponse(proxy, Response.success(response.body()))
            } else {
                callback.onResponse(proxy, Response.error(response.code(), response.errorBody()))
            }
        }

        override fun onFailure(call: Call<T>, error: Throwable) {
            callback.onFailure(call, error)
        }
    }

    override fun timeout(): Timeout {
        return proxy.timeout()
    }
}