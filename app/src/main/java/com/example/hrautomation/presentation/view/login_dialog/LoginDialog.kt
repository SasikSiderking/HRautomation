package com.example.hrautomation.presentation.view.login_dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.hrautomation.R
import com.example.hrautomation.databinding.DialogAuthBinding
import com.example.hrautomation.presentation.view.activity.appComponent
import javax.inject.Inject

class LoginDialog: DialogFragment() {

    @Inject
    lateinit var loginDialogViewModelProvider: LoginDialogViewModelProvider

    private val viewModel: LoginDialogViewModel by viewModels{
        loginDialogViewModelProvider
    }

    private var _binding: DialogAuthBinding? = null
    private val binding: DialogAuthBinding
        get() = _binding!!

    private val loadObserver = Observer<Boolean>{
           binding.progressBar.isVisible=it
    }
    private val uiStateObserver = Observer<Boolean>{
        binding.email.isVisible=it
        binding.okEmailButton.isVisible=it
        binding.code.isVisible=!it
        binding.okCodeButton.isVisible=!it
    }

    private val tokenStateObserver = Observer<Boolean> {
        dialog?.dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen)
        requireContext().appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.dialog_auth, container, false)
        binding.okEmailButton.setOnClickListener {
            viewModel.okEmailListener(binding.email.text.toString())
        }
        binding.okCodeButton.setOnClickListener{
            viewModel.okCodeListener(binding.email.text.toString(),binding.code.text.toString())
        }
        viewModel.isLoading.observe(viewLifecycleOwner,loadObserver)
        viewModel.uiState.observe(viewLifecycleOwner,uiStateObserver)
        viewModel.tokenState.observe(viewLifecycleOwner,tokenStateObserver)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR)
        isCancelable=false
        return dialog
    }

    override fun onDestroyView() {
        _binding?.unbind()
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val TAG = "AuthDialog"
    }
}