package com.example.hrautomation.utils

interface IMapper<In,Out> {
    fun convert(model: In): Out
}