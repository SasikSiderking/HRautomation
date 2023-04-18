package com.example.hrautomation.presentation.view.restaurants.сity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import android.widget.TextView.OnEditorActionListener
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.BottomSheetCitiesBinding
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.model.restaurants.CityItem
import com.example.hrautomation.utils.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class CityBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetCitiesBinding? = null
    private val binding: BottomSheetCitiesBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: CityViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var adapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomSheetCitiesBinding.inflate(inflater, container, false)

        initUi()
        initSearch()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialog = dialog
        if (dialog != null) {
            val bottomSheet: FrameLayout = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private val cityObserver = Observer<List<BaseListItem>> { cityList ->
        adapter.update(cityList)
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

    private fun initUi() {
        adapter = CityAdapter(onCityClickListener)
        viewModel.data.observe(viewLifecycleOwner, cityObserver)
    }

    private val onCityClickListener = OnCityClickListener { city: CityItem ->
        val bundle = Bundle()
        bundle.putSerializable(RESULT_KEY, city)
        setFragmentResult(REQUEST_KEY, bundle)
        this.dismiss()
    }

    private fun initSearch() {
        with(binding) {
            citiesRecyclerview.adapter = adapter
            citiesRecyclerview.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))

            editSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.performSearch(v.text.toString().trim())
                    true
                } else {
                    false
                }
            })
            editSearch.addTextChangedListener(textWatcher)
            clearText.setOnClickListener(View.OnClickListener { editSearch.text.clear() })
        }
    }

    companion object {
        const val TAG = "SearchBottomSheet"

        const val RESULT_KEY = "CityKey"

        const val REQUEST_KEY = "CityPick"

        fun newInstance() = CityBottomSheet()
    }
}