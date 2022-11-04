package com.example.hrautomation.utils

interface Mapper<In, Out> {
    fun convert(model: In): Out
}