package com.example.hrautomation.presentation.view.social

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.FragmentSocialBinding
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.fragment.BaseFragment
import com.example.hrautomation.utils.ui.switcher.ContentLoadingSettings
import com.example.hrautomation.utils.ui.switcher.ContentLoadingState
import com.example.hrautomation.utils.ui.switcher.base.SwitchAnimationParams

class SocialFragment : BaseFragment<FragmentSocialBinding>() {
    override val bindingInflater: (LayoutInflater) -> FragmentSocialBinding
        get() = { FragmentSocialBinding.inflate(layoutInflater) }

    private val viewModel: SocialViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var adapter: SocialAdapter

    override fun initInject() {
        (requireContext().applicationContext as App).appComponent.inject(this)
    }


    override fun initUI() {
        with(binding) {
            contentLoadingSwitcher.setup(
                ContentLoadingSettings(
                    contentViews = listOf(eventRecyclerView),
                    errorViews = listOf(reusableReload.reusableReload),
                    loadingViews = listOf(reusableLoading.progressBar),
                    initState = ContentLoadingState.LOADING
                )
            )
            reusableReload.reloadButton.setOnClickListener {
                viewModel.reload()
                contentLoadingSwitcher.switchState(ContentLoadingState.LOADING, SwitchAnimationParams(delay = 500L))
            }

            adapter = SocialAdapter()
            eventRecyclerView.adapter = adapter
            eventRecyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun initObserves() {
        viewModel.data.observe(viewLifecycleOwner, eventsObserver)
        viewModel.exception.observe(viewLifecycleOwner, exceptionObserver)
    }

    override fun initListeners() = Unit

    private val eventsObserver = Observer<List<BaseListItem>> { events ->
        adapter.update(events)
        contentLoadingSwitcher.switchState(ContentLoadingState.CONTENT, SwitchAnimationParams(delay = 500L))
    }

    private val exceptionObserver = Observer<Throwable?> { exception ->
        exception?.let {
            contentLoadingSwitcher.switchState(ContentLoadingState.ERROR, SwitchAnimationParams(delay = 500L))
        }
    }
}