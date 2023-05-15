package com.example.hrautomation.presentation.view.social.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.social.EventItem
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class EventDetailsViewModel @Inject constructor() : BaseViewModel() {
    val data: LiveData<EventItem>
        get() = _data
    private val _data: MutableLiveData<EventItem> = MutableLiveData()

    init {
        loadData()
    }

    private fun loadData() {
        val material1 = EventMaterialItem(
            1,
            "https://vk.com/kasheysmertniy",
            "Sigma male example"
        )
        val material2 = EventMaterialItem(
            2,
            "https://dota2.ru/esport/tournaments/",
            "Сайт с турнирами по плохой игре"
        )
        val testEventItem = EventItem(
            993,
            "Соревы по доте",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
            "15.06.23",
            "Улица Пушкина дом колотушкина",
            "offline",
            "https://cdn.game.tv/game-tv-content/images_2/mobile/game_banner/947730ec4740d9bea57d76fca436bbb8/en/947730ec4740d9bea57d76fca436bbb8.jpg",
            LatLng(56.4621202, 84.9672976),
            listOf(material1, material2)
        )
        _data.postValue(testEventItem)
    }

    fun reload() {
        loadData()
    }
}