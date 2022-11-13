package com.example.hrautomation.di.view_model_modules

import androidx.lifecycle.ViewModel
import com.example.hrautomation.presentation.view.colleagues.ColleaguesViewModel
import com.example.hrautomation.presentation.view.colleagues.employee.EmployeeViewModel
import com.example.hrautomation.presentation.view.faq.FaqViewModel
import com.example.hrautomation.presentation.view.faq.activity_question.QuestionViewModel
import com.example.hrautomation.presentation.view.loading.LoadingActivityViewModel
import com.example.hrautomation.presentation.view.loading.activity_login.LoginActivityViewModel
import com.example.hrautomation.presentation.view.loading.code.CodeLoginViewModel
import com.example.hrautomation.presentation.view.loading.email.EmailLoginViewModel
import com.example.hrautomation.presentation.view.meeting_room.MeetingRoomViewModel
import com.example.hrautomation.presentation.view.product.ProductFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

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
    @ViewModelKey(LoginActivityViewModel::class)
    fun bindLoginActivityViewModel(loginActivityViewModel: LoginActivityViewModel): ViewModel

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
    @ViewModelKey(ColleaguesViewModel::class)
    fun bindColleaguesFragmentViewModel(colleaguesViewModel: ColleaguesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EmployeeViewModel::class)
    fun bindEmployeeViewModel(employeeViewModel: EmployeeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoadingActivityViewModel::class)
    fun bindLoadingActivityViewModel(loadingActivityViewModel: LoadingActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FaqViewModel::class)
    fun bindFaqViewModel(faqViewModel: FaqViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QuestionViewModel::class)
    fun bindQuestionViewModel(questionViewModel: QuestionViewModel): ViewModel
}
