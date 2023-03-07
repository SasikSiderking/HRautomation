package com.example.hrautomation.presentation.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.hrautomation.utils.ViewModelFactory
import com.example.hrautomation.utils.ui.switcher.ContentLoadingStateSwitcher
import javax.inject.Inject

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: ViewBinding? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = (_binding as VB?)!!

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

    }

    abstract fun initInject()

    abstract fun initUI()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}