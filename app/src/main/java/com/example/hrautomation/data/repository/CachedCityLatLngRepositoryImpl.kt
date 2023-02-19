package com.example.hrautomation.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.hrautomation.domain.repository.CachedCityLatLngRepository
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CachedCityLatLngRepositoryImpl @Inject constructor(private val context: Context) : CachedCityLatLngRepository {

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            PREF_ACCESS,
            Context.MODE_PRIVATE
        )
    }

    override fun setLatLng(latLng: LatLng) {
        preferences.edit().putString(LATITUDE, latLng.latitude.toString()).apply()
        preferences.edit().putString(LONGITUDE, latLng.longitude.toString()).apply()
    }

    override fun getLatLng(): LatLng? {
        val lat = preferences.getString(LATITUDE, null)?.toDouble()
        val lng = preferences.getString(LONGITUDE, null)?.toDouble()

        return if (lat != null && lng != null) {
            LatLng(lat, lng)
        } else {
            null
        }
    }

    override fun setCityId(cityId: Long) {
        preferences.edit().putString(CITY_ID, cityId.toString()).apply()
    }

    override fun getCityId(): Long? {
        return preferences.getString(CITY_ID, null)?.toLong()
    }

    private companion object {
        const val PREF_ACCESS = "cityLatLng"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val CITY_ID = "cityId"
    }
}