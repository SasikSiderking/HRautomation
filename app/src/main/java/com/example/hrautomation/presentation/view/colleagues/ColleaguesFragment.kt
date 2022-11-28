package com.example.hrautomation.presentation.view.colleagues

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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

        initUi()
        viewModel.data.observe(viewLifecycleOwner, colleaguesObserver)



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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initUi() {
        contentLoadingSwitcher.setup(
            ContentLoadingSettings(
                contentViews = listOf(binding.colleaguesRecyclerview),
                loadingViews = listOf(binding.progressBar),
                initState = ContentLoadingState.LOADING
            )
        )

        adapter = ColleaguesAdapter(OnColleagueClickListener { colleague ->
            startActivity(EmployeeActivity.createIntent(requireContext(), colleague.id))
        })
        binding.colleaguesRecyclerview.adapter = adapter

        binding.editSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.performSearch(v.text.toString().trim())
                true
            } else {
                false
            }
        })

        binding.editSearch.addTextChangedListener(textWatcher)

        binding.clearText.setOnClickListener(View.OnClickListener { binding.editSearch.text.clear() })
    }
}