package com.example.hrautomation.presentation.view.meeting_room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.hrautomation.R
import com.example.hrautomation.databinding.FragmentMeetingRoomBinding
import com.example.hrautomation.presentation.view.activity.appComponent
import com.example.hrautomation.presentation.view.login_dialog.LoginDialog
import javax.inject.Inject

class MeetingRoomFragment : Fragment() {

    private var _binding: FragmentMeetingRoomBinding? = null
    private val binding: FragmentMeetingRoomBinding
        get() = _binding!!

    @Inject
    lateinit var meetingRoomViewModelProvider: MeetingRoomViewModelProvider

    private val viewModel: MeetingRoomViewModel by viewModels {
        meetingRoomViewModelProvider
    }

    private var loginDialog: LoginDialog? = null

    private val tokenObserver = Observer<Boolean>{
        if(!it){
            loginDialog?.dismiss()
            loginDialog = LoginDialog()
            loginDialog?.show(childFragmentManager,LoginDialog.TAG)
        } else{
            loginDialog?.dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.isTokenExist.observe(viewLifecycleOwner,tokenObserver)
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meeting_room, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _binding?.unbind()
        _binding = null
        loginDialog = null
        viewModel.isTokenExist.removeObserver(tokenObserver)
        super.onDestroyView()
    }
}