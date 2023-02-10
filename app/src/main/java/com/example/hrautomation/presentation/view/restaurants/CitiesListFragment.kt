package com.example.hrautomation.presentation.view.restaurants

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.hrautomation.app.App
import com.example.hrautomation.databinding.FragmentRestaurantsCitiesBinding
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.utils.ViewModelFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class CitiesListFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentRestaurantsCitiesBinding? = null
    private val binding: FragmentRestaurantsCitiesBinding
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
        _binding = FragmentRestaurantsCitiesBinding.inflate(inflater, container, false)

        initUi()
        initSearch()

        return binding.root
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
        adapter = CityAdapter(OnCityClickListener { latLng: LatLng ->
            val bundle = Bundle()
            bundle.putDouble(LATITUDE_KEY, latLng.latitude)
            bundle.putDouble(LONGITUDE_KEY, latLng.longitude)
            setFragmentResult(CITIES_FRAGMENT_KEY, bundle)
            this.dismiss()
        })
        viewModel.data.observe(viewLifecycleOwner, cityObserver)
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

        const val CITIES_FRAGMENT_KEY = "CitiesKey"

        const val LATITUDE_KEY = "LatitudeKey"

        const val LONGITUDE_KEY = "LongitudeKey"

        fun newInstance() = CitiesListFragment()
    }
}