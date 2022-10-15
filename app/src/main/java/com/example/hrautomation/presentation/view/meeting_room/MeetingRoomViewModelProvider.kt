package com.example.hrautomation.presentation.view.meeting_room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hrautomation.data.repository.UserRepository
import com.example.hrautomation.domain.repository.IUserRepository
import javax.inject.Inject

class MeetingRoomViewModelProvider @Inject constructor(private val repo: IUserRepository): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MeetingRoomViewModel(repo) as T
    }
}