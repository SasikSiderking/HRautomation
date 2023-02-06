package com.example.hrautomation.presentation.view.restaurants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hrautomation.databinding.ItemCityBinding
import com.example.hrautomation.presentation.base.delegates.BaseItemAdapterDelegate
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.delegates.ClickableViewHolder
import com.example.hrautomation.presentation.base.delegates.OnViewHolderClickListener
import com.example.hrautomation.presentation.model.restaurants.CityItem
import com.example.hrautomation.presentation.view.restaurants.CityItemAdapterDelegate.CityViewHolder
import com.google.android.gms.maps.model.LatLng

class CityItemAdapterDelegate(private val onCityClickListener: OnCityClickListener) :
    BaseItemAdapterDelegate<CityItem, CityViewHolder>(), OnViewHolderClickListener<CityViewHolder> {

    override fun isForViewType(item: BaseListItem): Boolean {
        return item is CityItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): CityViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding, this)
    }

    override fun onBind(item: CityItem, holder: CityViewHolder, payloads: List<Any>) {
        holder.cityName.text = item.name
    }

    override fun onViewHolderClick(view: View, holder: CityViewHolder) {
        val item = getItemForViewHolder(holder)
        onCityClickListener.onClick(LatLng(item.lat, item.lng))
    }

    class CityViewHolder(binding: ItemCityBinding, clickListener: OnViewHolderClickListener<CityViewHolder>) :
        ClickableViewHolder<CityViewHolder>(binding.root, clickListener) {
        val cityName: TextView

        init {
            cityName = binding.city
        }
    }
}