package com.example.hrautomation.utils.retrofit_adapter

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class CustomAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        returnType.let {
            try {
                val enclosingType = (it as ParameterizedType)

                val type = enclosingType.actualTypeArguments[0]
                return CustomCallAdapter<Any?>(type)
            } catch (ex: ClassCastException) {
                return null
            }
        }
    }
}

private class CustomCallAdapter<R>(private val type: Type) : CallAdapter<R, Call<R>> {

    override fun responseType() = type

    override fun adapt(call: Call<R>): Call<R> = CustomCall(call)
}