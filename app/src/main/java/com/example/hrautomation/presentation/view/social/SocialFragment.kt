package com.example.hrautomation.presentation.view.social

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.FragmentSocialBinding
import com.example.hrautomation.presentation.base.fragment.BaseFragment
import com.example.hrautomation.utils.ui.switcher.ContentLoadingSettings
import com.example.hrautomation.utils.ui.switcher.ContentLoadingState
import com.example.hrautomation.utils.ui.switcher.base.SwitchAnimationParams

class SocialFragment : BaseFragment<FragmentSocialBinding>() {
    override val bindingInflater: (LayoutInflater) -> FragmentSocialBinding
        get() = { FragmentSocialBinding.inflate(layoutInflater) }

    private val viewModel: SocialFragmentViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var adapter: SocialFragmentAdapter

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

            adapter = SocialFragmentAdapter()
            eventRecyclerView.adapter = adapter
            eventRecyclerView.layoutManager = GridLayoutManager(context, NUMBER_OF_COLUMNS)
        }
    }

    override fun initObserves() {
        TODO("Not yet implemented")
    }

    override fun initListeners() {
        TODO("Not yet implemented")
    }

    companion object {
        private const val NUMBER_OF_COLUMNS = 2
    }
}