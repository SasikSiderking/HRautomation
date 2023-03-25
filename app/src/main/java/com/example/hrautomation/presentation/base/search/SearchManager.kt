package com.example.hrautomation.presentation.base.search

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.tryLaunch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext

abstract class SearchManager<I : BaseListItem, VB : ViewBinding, R>(
    protected val uiDelegate: SearchUiDelegate<I, VB, BaseSearchViewHolder<VB>>,
    protected val loaderDelegate: SearchLoaderDelegate<R>
) : CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()

    private val resultAdapter: SearchUiResultAdapter<I, VB> = SearchUiResultAdapter(uiDelegate)

    private var searchView: WeakReference<EditText>? = null
    private var resultContainer: WeakReference<RecyclerView>? = null

    private var isAttached: Boolean = false

    open fun attach(newSearchView: EditText, newResultContainer: RecyclerView) {
        searchView?.get()?.removeTextChangedListener(textWatcher)
        resultContainer?.get()?.adapter = null

        searchView = WeakReference(newSearchView)
        resultContainer = WeakReference(newResultContainer)

        newResultContainer.adapter = resultAdapter
        newResultContainer.layoutManager = LinearLayoutManager(newResultContainer.context)
        newSearchView.addTextChangedListener(textWatcher)

        isAttached = true
        onAttached()
    }

    open fun detach() {
        searchView?.get()?.removeTextChangedListener(textWatcher)
        resultContainer?.get()?.adapter = null

        searchView = null
        resultContainer = null

        isAttached = false
        onDetached()
    }

    protected fun processSearch(request: String) {
        if (isAttached) {
            tryLaunch(
                doOnLaunch = {
                    onLoadingStarted()
                    val items = withContext(Dispatchers.IO) {
                        val data = loaderDelegate.loadByRequest(request)
                        convertToItems(data)
                    }
                    resultAdapter.setItems(items)
                    onLoadingFinished()
                },
                doOnError = { error -> Timber.e(error) }
            )

        }
    }

    abstract fun convertToItems(data: R): List<I>

    open fun onAttached() = Unit
    open fun onDetached() = Unit
    open fun onLoadingStarted() = Unit
    open fun onLoadingFinished() = Unit

    protected open val textWatcher = object : TextWatcher {

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun afterTextChanged(editableText: Editable?) {
            val request = editableText.toString()
            processSearch(request)
        }
    }
}