package com.example.hrautomation.presentation.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.ViewModelFactory
import com.example.hrautomation.utils.ui.switcher.ContentLoadingState
import com.example.hrautomation.utils.ui.switcher.ContentLoadingStateSwitcher
import com.example.hrautomation.utils.ui.switcher.base.SwitchAnimationParams
import javax.inject.Inject

abstract class DetailedItemActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: ViewBinding? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = (_binding as VB?)!!

    abstract val bindingInflater: (LayoutInflater) -> VB

    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    protected val contentLoadingSwitcher: ContentLoadingStateSwitcher = ContentLoadingStateSwitcher()

    protected val selectedItemId: Long by lazy { intent.getLongExtra(ID_EXTRA, 0L) }

    protected val exceptionObserver = Observer<Throwable?> { exception ->
        exception?.let {
            contentLoadingSwitcher.switchState(ContentLoadingState.ERROR, SwitchAnimationParams(delay = 500L))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInject()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)

        initUI()

        initObserves()
    }

    abstract fun initInject()

    abstract fun initUI()

    abstract fun initObserves()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract val selectedItemObserver: Observer<BaseListItem>

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val ID_EXTRA = "selectedItemId"
    }
}