package com.example.hrautomation.utils.publisher

sealed class ProfileEvent {
    object Update : ProfileEvent()
}