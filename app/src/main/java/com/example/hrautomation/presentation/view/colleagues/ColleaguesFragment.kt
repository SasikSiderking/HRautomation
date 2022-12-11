package com.example.hrautomation.presentation.view.colleagues

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.FragmentColleaguesBinding
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.view.colleagues.employee.EmployeeActivity
import com.example.hrautomation.utils.ViewModelFactory
import com.example.hrautomation.utils.ui.switcher.ContentLoadingSettings
import com.example.hrautomation.utils.ui.switcher.ContentLoadingState
import com.example.hrautomation.utils.ui.switcher.ContentLoadingStateSwitcher
import com.example.hrautomation.utils.ui.switcher.base.SwitchAnimationParams
import javax.inject.Inject


class ColleaguesFragment : Fragment() {

    private var _binding: FragmentColleaguesBinding? = null
    private val binding: FragmentColleaguesBinding
        get() = _binding!!

    private lateinit var adapter: ColleaguesAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ColleaguesViewModel by viewModels {
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
        _binding = FragmentColleaguesBinding.inflate(inflater, container, false)

        initToolbar()
        initUi()

        return binding.root
    }

    private var textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val searchReq = s.toString().trim()

            binding.clearText.isVisible = searchReq.isNotEmpty()
            viewModel.performSearch(searchReq)
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    private val colleaguesObserver = Observer<List<BaseListItem>> { updatedDataSet ->
        adapter.update(updatedDataSet)
        contentLoadingSwitcher.switchState(ContentLoadingState.CONTENT, SwitchAnimationParams(delay = 500L))
    }

    private val exceptionObserver = Observer<Throwable?> { exception ->
        exception?.let {
            contentLoadingSwitcher.switchState(ContentLoadingState.ERROR, SwitchAnimationParams(delay = 500L))
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.let {
            it.elevation = 0F
        }
    }

    private fun initUi() {
        with(binding) {
            contentLoadingSwitcher.setup(
                ContentLoadingSettings(
                    contentViews = listOf(colleaguesRecyclerview, searchContainer),
                    errorViews = listOf(reusableReload.reusableReload),
                    loadingViews = listOf(reusableLoading.progressBar),
                    initState = ContentLoadingState.LOADING
                )
            )

            adapter = ColleaguesAdapter(OnColleagueClickListener { colleague ->
                startActivity(EmployeeActivity.createIntent(requireContext(), colleague.id))
            })
            colleaguesRecyclerview.adapter = adapter
            colleaguesRecyclerview.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))

            editSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.performSearch(v.text.toString().trim())
                    true
                } else {
                    false
                }
            })

            editSearch.addTextChangedListener(textWatcher)

            reusableReload.reloadButton.setOnClickListener {
                viewModel.reload()
                contentLoadingSwitcher.switchState(ContentLoadingState.LOADING, SwitchAnimationParams(delay = 500L))
            }

            clearText.setOnClickListener(View.OnClickListener { editSearch.text.clear() })
            viewModel.data.observe(viewLifecycleOwner, colleaguesObserver)
            viewModel.exception.observe(viewLifecycleOwner, exceptionObserver)
        }
    }
}