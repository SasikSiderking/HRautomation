package com.example.hrautomation.presentation.base.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.hrautomation.presentation.base.delegates.BaseListItem

interface SearchUiDelegate<Item : BaseListItem, VB : ViewBinding, VH : RecyclerView.ViewHolder> {

    fun getLayout(parent: ViewGroup, inflater: LayoutInflater): VB

    fun bind(item: Item, holder: VH)
}