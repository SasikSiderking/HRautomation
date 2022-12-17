package com.example.hrautomation.presentation.view.loading.code

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.hrautomation.R
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.FragmentLoadingCodeBinding
import com.example.hrautomation.presentation.view.activity.MainActivity
import com.example.hrautomation.utils.ViewModelFactory
import javax.inject.Inject

class CodeLoginFragment : Fragment() {
    private var _binding: FragmentLoadingCodeBinding? = null
    private val binding: FragmentLoadingCodeBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: CodeLoginViewModel by viewModels {
        viewModelFactory
    }

    private val email by lazy { requireArguments().getString(EMAIL_EXTRA)!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoadingCodeBinding.inflate(inflater, container, false)

        initUi()

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun checkCode() {
        setFieldsVisibility(false)
        viewModel.checkCode(email, binding.code.text.toString())
    }

    private val codeCheckObserver = Observer<Boolean> { isCodeValid ->
        if (isCodeValid) {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
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
        with(binding) {
            code.isEnabled = flag
            okCodeButton.isEnabled = flag
            progressBar.isVisible = !flag
        }
    }

    private fun initUi() {
        binding.okCodeButton.setOnClickListener { checkCode() }

        viewModel.isCodeCheckSuccess.observe(viewLifecycleOwner, codeCheckObserver)
        viewModel.exception.observe(viewLifecycleOwner, exceptionObserver)
    }

    companion object {
        private const val EMAIL_EXTRA = "email"

        fun prepareBundle(email: String): Bundle {
            return Bundle().apply {
                putString(EMAIL_EXTRA, email)
            }
        }
    }
}