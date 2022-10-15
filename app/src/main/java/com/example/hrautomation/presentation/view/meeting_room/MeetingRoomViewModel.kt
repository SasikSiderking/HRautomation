package com.example.hrautomation.presentation.view.meeting_room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.repository.UserRepository
import com.example.hrautomation.domain.repository.IUserRepository
import kotlinx.coroutines.launch

class MeetingRoomViewModel(private val repo: IUserRepository): ViewModel() {
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

    private fun loadData(){

    }

    private fun findToken(){
        val token = repo.getToken()
        token?.let {

            _isTokenExist.postValue(true)
        }?:apply{
            _isTokenExist.postValue(false)
        }
    }
}