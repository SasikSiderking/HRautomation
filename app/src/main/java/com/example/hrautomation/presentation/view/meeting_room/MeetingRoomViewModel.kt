package com.example.hrautomation.presentation.view.meeting_room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.repository.UserRepository
import com.example.hrautomation.domain.repository.IUserRepository
import kotlinx.coroutines.launch

class MeetingRoomViewModel(private val repo: IUserRepository): ViewModel() {
    val text: LiveData<String>
    get() = _text
    private val _text = MutableLiveData<String>()

    val isTokenExist: LiveData<Boolean>
    get() = _isTokenExist
    private val _isTokenExist = MutableLiveData<Boolean>()

    init {
        start()
    }

    private fun start(){
        viewModelScope.launch {
            findToken()
        }
    }

    private suspend fun findToken(){
        val token = repo.getToken()
        token?.let {
            loadData()
            _isTokenExist.postValue(true)
        }?:apply{
            _isTokenExist.postValue(false)
        }
    }

    private fun loadData(){
        _text.postValue("Downloaded data")
    }
}