package com.example.hrautomation.presentation.view.loading.email

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.hrautomation.R
import com.example.hrautomation.databinding.FragmentLoadingEmailBinding
import com.example.hrautomation.presentation.view.activity.appComponent
import com.example.hrautomation.utils.ViewModelFactory
import javax.inject.Inject

class EmailLogin : Fragment() {
    private var _binding: FragmentLoadingEmailBinding? = null
    private val binding: FragmentLoadingEmailBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel: EmailLoginViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireContext().appComponent.inject(this)

        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_loading_email, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.okEmailButton.setOnClickListener { checkEmail() }

        viewModel.isEmailCheckSuccess.observe(viewLifecycleOwner, emailCheckObserver)
    }

    override fun onDestroyView() {
        _binding?.unbind()
        _binding = null
        super.onDestroyView()
    }

    private fun checkEmail() {
        binding.email.isEnabled = false
        binding.email.inputType = 0
        binding.progressBar.visibility = View.VISIBLE
        viewModel.checkEmail(binding.email.text.toString())
    }

    private val emailCheckObserver = Observer<Boolean> {
        if (it) {
            val bundle = Bundle()
            bundle.putString("email", binding.email.text.toString())
            findNavController().navigate(R.id.action_emailLogin_to_codeLogin, bundle)
        }
        binding.email.isEnabled = true
        binding.email.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        binding.progressBar.visibility = View.INVISIBLE
    }
}