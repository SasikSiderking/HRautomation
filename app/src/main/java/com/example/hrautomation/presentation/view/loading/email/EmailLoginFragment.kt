package com.example.hrautomation.presentation.view.loading.email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.hrautomation.R
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.FragmentLoadingEmailBinding
import com.example.hrautomation.presentation.view.loading.code.CodeLoginFragment
import com.example.hrautomation.utils.ViewModelFactory
import javax.inject.Inject

class EmailLoginFragment : Fragment() {
    private var _binding: FragmentLoadingEmailBinding? = null
    private val binding: FragmentLoadingEmailBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: EmailLoginViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireContext().applicationContext as App).appComponent.inject(this)

        _binding = FragmentLoadingEmailBinding.inflate(inflater, container, false)

        initUi()

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun checkEmail() {
        setFieldsVisibility(false)
        viewModel.checkEmail(binding.email.text.toString())
    }

    private val emailCheckObserver = Observer<Boolean> { isEmailValid ->
        if (isEmailValid) {
            findNavController().navigate(R.id.action_emailLogin_to_codeLogin, CodeLoginFragment.prepareBundle(binding.email.text.toString()))
        }
        setFieldsVisibility(true)
    }

    private val exceptionObserver = Observer<Throwable?> { exception ->
        exception?.let {
            Toast.makeText(context, R.string.toast_overall_error, Toast.LENGTH_SHORT).show()
            viewModel.clearExceptionState()
        }
    }

    private fun setFieldsVisibility(flag: Boolean) {
        binding.email.isEnabled = flag
        binding.progressBar.isVisible = !flag
    }

    private fun initUi() {
        binding.okEmailButton.setOnClickListener { checkEmail() }

        viewModel.isEmailCheckSuccess.observe(viewLifecycleOwner, emailCheckObserver)
        viewModel.exception.observe(viewLifecycleOwner, exceptionObserver)
    }
}