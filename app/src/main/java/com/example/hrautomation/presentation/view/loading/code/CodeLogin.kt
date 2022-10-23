package com.example.hrautomation.presentation.view.loading.code

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hrautomation.R
import com.example.hrautomation.databinding.FragmentLoadingCodeBinding
import com.example.hrautomation.presentation.view.activity.MainActivity
import com.example.hrautomation.presentation.view.activity.appComponent
import com.example.hrautomation.presentation.view.loading.activity_load.LoadingActivityViewModel
import com.example.hrautomation.presentation.view.loading.email.EmailLoginViewModel
import com.example.hrautomation.utils.ViewModelFactory
import javax.inject.Inject

class CodeLogin: Fragment() {
    private var _binding: FragmentLoadingCodeBinding? = null
    private val binding: FragmentLoadingCodeBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel: CodeLoginViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireContext().appComponent.inject(this)

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_loading_code, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.okCodeButton.setOnClickListener { checkCode() }

        viewModel.isCodeCheckSuccess.observe(viewLifecycleOwner,codeCheckObserver)
    }

    override fun onDestroyView() {
        _binding?.unbind()
        _binding = null
        super.onDestroyView()
    }

    private fun checkCode(){
        binding.code.isEnabled=false
        binding.code.inputType=0
        binding.progressBar.visibility=View.VISIBLE
        arguments?.getString("email")?.let { viewModel.checkCode(it,binding.code.text.toString()) }
    }

    private val codeCheckObserver = Observer<Boolean> {
        if (it) {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        binding.code.isEnabled=true
        binding.code.inputType=InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        binding.progressBar.visibility=View.INVISIBLE
    }
}