package com.example.hrautomation.presentation.model.social.event

import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.google.android.gms.maps.model.LatLng

data class EventItemMap(
    override val id: Long,
    val latLng: LatLng
) : BaseListItem {
    companion object {
        const val delegateClassNumber = 4
    }
}