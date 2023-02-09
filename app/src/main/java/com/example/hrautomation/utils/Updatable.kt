package com.example.hrautomation.utils

interface Updatable<ItemType> {
    fun update(item: ItemType)
}