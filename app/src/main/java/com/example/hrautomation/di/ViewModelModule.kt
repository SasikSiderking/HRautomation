package com.example.hrautomation.di

import androidx.lifecycle.ViewModel
import com.example.hrautomation.presentation.view.colleagues.ColleaguesFragmentViewModel
import com.example.hrautomation.presentation.view.loading.activity_load.LoadingActivityViewModel
import com.example.hrautomation.presentation.view.loading.code.CodeLoginViewModel
import com.example.hrautomation.presentation.view.loading.email.EmailLoginViewModel
import com.example.hrautomation.presentation.view.meeting_room.MeetingRoomViewModel
import com.example.hrautomation.presentation.view.product.ProductFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProductFragmentViewModel::class)
    fun bindProductFragmentViewModel(productFragmentViewModel: ProductFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MeetingRoomViewModel::class)
    fun bindMeetingRoomViewModel(meetingRoomViewModel: MeetingRoomViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoadingActivityViewModel::class)
    fun bindLoadingActivityViewModel(loadingActivityViewModel: LoadingActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EmailLoginViewModel::class)
    fun bindEmailLoginViewModel(emailLoginViewModel: EmailLoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CodeLoginViewModel::class)
    fun bindCodeLoginViewModel(codeLoginViewModel: CodeLoginViewModel): ViewModel

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(ColleaguesFragmentViewModel::class)
    fun bindColleaguesFragmentViewModel(colleaguesFragmentViewModel: ColleaguesFragmentViewModel): ViewModel
}
