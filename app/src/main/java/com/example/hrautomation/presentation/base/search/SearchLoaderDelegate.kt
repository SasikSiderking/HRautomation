package com.example.hrautomation.presentation.base.search

interface SearchLoaderDelegate<R> {

    suspend fun loadByRequest(searchRequest: String): R
}