package com.example.hrautomation.presentation.view.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.hrautomation.R
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.FragmentFaqBinding
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.view.faq.activity_question.QuestionActivity
import com.example.hrautomation.utils.ViewModelFactory
import com.example.hrautomation.utils.ui.switcher.ContentLoadingSettings
import com.example.hrautomation.utils.ui.switcher.ContentLoadingState
import com.example.hrautomation.utils.ui.switcher.ContentLoadingStateSwitcher
import com.example.hrautomation.utils.ui.switcher.base.SwitchAnimationParams
import javax.inject.Inject

class FaqFragment : Fragment() {

    private var _binding: FragmentFaqBinding? = null
    private val binding: FragmentFaqBinding
        get() = _binding!!

    private lateinit var adapter: FaqAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: FaqViewModel by viewModels {
        viewModelFactory
    }

    private val contentLoadingSwitcher: ContentLoadingStateSwitcher = ContentLoadingStateSwitcher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFaqBinding.inflate(inflater, container, false)

        initUi()

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initUi() {
        with(binding) {
            contentLoadingSwitcher.setup(
                ContentLoadingSettings(
                    contentViews = listOf(faqRecyclerview),
                    errorViews = listOf(reusableReload.reusableReload),
                    loadingViews = listOf(reusableLoading.progressBar),
                    initState = ContentLoadingState.LOADING
                )
            )
            reusableReload.reloadButton.setOnClickListener {
                viewModel.reload()
                contentLoadingSwitcher.switchState(ContentLoadingState.LOADING, SwitchAnimationParams(delay = 500L))
            }
        }
        adapter = FaqAdapter(OnFaqCategoryClickListener { id: Long, name: String ->
            startActivity(QuestionActivity.createIntent(requireContext(), id, name))
        })
        binding.faqRecyclerview.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner, categoryObserver)
        viewModel.exception.observe(viewLifecycleOwner, exceptionObserver)
    }

    private val categoryObserver = Observer<List<BaseListItem>> { newItems ->
        adapter.update(newItems)
        contentLoadingSwitcher.switchState(ContentLoadingState.CONTENT, SwitchAnimationParams(delay = 500L))
    }

    private val exceptionObserver = Observer<Throwable?> { exception ->
        exception?.let {
            Toast.makeText(requireContext(), R.string.toast_overall_error, Toast.LENGTH_LONG).show()
            viewModel.clearExceptionState()
            contentLoadingSwitcher.switchState(ContentLoadingState.ERROR, SwitchAnimationParams(delay = 500L))
        }
    }
}