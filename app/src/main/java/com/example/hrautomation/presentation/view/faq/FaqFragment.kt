package com.example.hrautomation.presentation.view.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.FragmentFaqBinding
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.view.faq.question.QuestionActivity
import com.example.hrautomation.utils.ViewModelFactory
import com.example.hrautomation.utils.ui.Dp
import com.example.hrautomation.utils.ui.dpToPx
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

        initToolbar()
        initUi()

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.let {
            it.elevation = requireContext().dpToPx(TOOLBAR_ELEVATION).toFloat()
        }
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
            faqRecyclerview.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
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
            contentLoadingSwitcher.switchState(ContentLoadingState.ERROR, SwitchAnimationParams(delay = 500L))
        }
    }

    private companion object {
        @Dp
        const val TOOLBAR_ELEVATION = 4F
    }
}