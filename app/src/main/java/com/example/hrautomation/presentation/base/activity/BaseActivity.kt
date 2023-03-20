package com.example.hrautomation.presentation.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.example.hrautomation.utils.ViewModelFactory
import com.example.hrautomation.utils.ui.switcher.ContentLoadingStateSwitcher
import javax.inject.Inject

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null

    protected val binding: VB
        get() = _binding!!

    abstract val bindingInflater: (LayoutInflater) -> VB

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected val contentLoadingSwitcher: ContentLoadingStateSwitcher = ContentLoadingStateSwitcher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initInject()

        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)

        initUI()

        initObserves()

        initListeners()

    }

    abstract fun initInject()

    abstract fun initUI()

    abstract fun initObserves()

    abstract fun initListeners()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected abstract val exceptionObserver: Observer<Throwable?>
}