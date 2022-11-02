package com.example.hrautomation.domain.repository

import com.example.hrautomation.presentation.base.delegates.BaseListItem


interface IProductRepository {

    suspend fun getProductItemList(): List<BaseListItem>
}