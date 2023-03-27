package com.example.hrautomation.presentation.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.hrautomation.utils.ViewModelFactory
import com.example.hrautomation.utils.ui.switcher.ContentLoadingStateSwitcher
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    private var _binding: VB? = null

    protected val binding: VB
        get() = _binding!!

    abstract val bindingInflater: (LayoutInflater) -> VB

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected val contentLoadingSwitcher: ContentLoadingStateSwitcher = ContentLoadingStateSwitcher()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initInject()

        _binding = bindingInflater.invoke(layoutInflater)

        initUI()

        initObserves()

        initListeners()
        return binding.root
    }

    abstract fun initInject()

    abstract fun initUI()

    abstract fun initObserves()

    abstract fun initListeners()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}